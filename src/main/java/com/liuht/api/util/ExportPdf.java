package com.liuht.api.util;

import com.alibaba.fastjson.JSONArray;
import com.lowagie.text.*;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.liuht.api.common.domain.*;
import com.liuht.ec.base.util.HYConst;

import java.awt.*;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author:TANQINGPING
 * @version:1.0 2017/1/5
 * package:com.liuht.api.util
 * <p>Title: ExportPdf.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2016</p>
 *
 */

public class ExportPdf<T> {

    private final float LEFT_SPACING = 50;//文档左边距离
    private final float RIGHT_SPACING = 50;//文档右边距离
    private final float TOP_SPACING = 50;//文档上边距离
    private final float BOTTOM_SPACING = 50;//文档下边距离
    private final int DOC_TITLE_SIZE = 24;//标题字体大小
    private final int FIRST_TITLE_SIZE = 20;//一级标题字体大小
    private final int SECOND_TITLE_SIZE = 18;//二级标题字体大小
    private final int THIRD_TITLE_SIZE = 16;//三级标题字体大小
    private final int CONTENT_SIZE = 12;//正文标题字体大小
    private final float HEAD_ROW_HEIGHT = 28;//表头高度
    private final float ROW_HEIGHT = 24;//行高

    public void exportPdf(String title, Collection<T> dataSet, OutputStream out) {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A4页面大小
        Document document = new Document(rectPageSize, LEFT_SPACING, RIGHT_SPACING, TOP_SPACING, BOTTOM_SPACING);// 其余4个参数(left,right,top,bottom)，设置了页面的4个边距
        try {
            PdfWriter.getInstance(document, out);
            document.open();
            /*设置footer*/
            HeaderFooter footer = new HeaderFooter(new PdfParagraph(""), true);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setBorder(0);
            document.setFooter(footer);
            /*文档标题*/
            PdfParagraph titleP = new PdfParagraph(title + "\n", DOC_TITLE_SIZE, true);
            titleP.setAlignment(Element.ALIGN_CENTER);
            document.add(titleP);
            /*开头提示内容*/
            PdfParagraph beFenvP = new PdfParagraph("文档说明", SECOND_TITLE_SIZE, false);
            document.add(beFenvP);
            PdfParagraph envvP = new PdfParagraph("环境变量运行在URL中,你可以配置多个(线上、灰度、开发)环境变量。在URL中使用方式$变量名$,例：" +
                    "\n" + "线上环境：prefix => http://www.liuht.com 则\n" + "请求URL：$prefix$/say => http://www.liuht.com/say" + "\n", CONTENT_SIZE, false);
            envvP.setAlignment(Element.ALIGN_LEFT);
            document.add(envvP);
            /*依次放chapter*/
            int chm = 1;
            PdfParagraph envP = new PdfParagraph("全局环境变量", FIRST_TITLE_SIZE, false);
            envP.setAlignment(Element.ALIGN_LEFT);
            Chapter chapter = new Chapter(envP, chm++);
            chapter.setTriggerNewPage(false);
            /*组装数据*/
            Iterator<T> it = dataSet.iterator();
            while (it.hasNext()) {
                T t = (T) it.next();
                Field[] fields = t.getClass().getDeclaredFields();
                Field declaredField = t.getClass().getDeclaredField("environments");
                /*为了让环境变量放在最前面，故在此交换位置*/
                for (short r = 0; r < fields.length; r++) {
                    if (fields[r] == declaredField) {
                        Field tt = fields[0];
                        fields[0] = fields[r];
                        fields[r] = tt;
                    }
                }
                /*通过反射获取相应值*/
                for (short i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get"
                            + fieldName.substring(0, 1).toUpperCase()
                            + fieldName.substring(1);
                    Class tCls = t.getClass();
                    try {
                        Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                        Object value = getMethod.invoke(t, new Object[]{});
                        if (value instanceof String) {
                            document.add(new PdfParagraph(value.toString(), CONTENT_SIZE, false));
                        } else if (value instanceof List) {
                            List values = (List) value;
                            /*循环获取环境参数或模块信息*/
                            for (int n = 0; n < values.size(); n++) {
                                Object o = values.get(n);
                                if (o instanceof Environments) {
                                    Environments environments = (Environments) o;
                                    PdfParagraph eTitlePara = new PdfParagraph(environments.getName(), SECOND_TITLE_SIZE, false);
                                    chapter.add(eTitlePara);
                                    List<EnvironmentsList> items = environments.getItems();
                                    /*循环获取配置的环境参数*/
                                    for (EnvironmentsList item : items) {
                                        PdfParagraph paramPara = new PdfParagraph(item.getParamname() + "    " + item.getParamvalue(), CONTENT_SIZE, false);
                                        chapter.add(paramPara);
                                    }
                                } else if (o instanceof Module) {
                                    Module m = (Module) o;
                                    PdfParagraph modulePara = new PdfParagraph(m.getName(), FIRST_TITLE_SIZE, false);
                                    chapter = new Chapter(modulePara, chm++);
                                    chapter.setTriggerNewPage(false);
                                    List<InterfaceFolder> folders = m.getFolders();
                                    /*循环获取分类信息*/
                                    for (int k = 0; k < folders.size(); k++) {
                                        InterfaceFolder folder = folders.get(k);
                                        PdfParagraph folderPara = new PdfParagraph(folder.getName(), SECOND_TITLE_SIZE, false);
                                        Section section1 = chapter.addSection(folderPara);
                                        List<InterfaceWithBLOBs> interfaces = folder.getInterfaces();
                                        /*循环获取接口信息*/
                                        for (int p = 0; p < interfaces.size(); p++) {
                                            InterfaceWithBLOBs anInterface = interfaces.get(p);
                                            PdfParagraph interfacePara = new PdfParagraph(anInterface.getName(), THIRD_TITLE_SIZE, false);
                                            Section section = section1.addSection(interfacePara);
                                            section.setIndentation(10);
                                            section.setIndentationLeft(10);
                                            section.setBookmarkOpen(false);
                                            section.setNumberStyle(Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
                                            /*接口基本信息*/
                                            section.add(new PdfParagraph("基本信息", CONTENT_SIZE, false));
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                            String contents = "更新时间：" + simpleDateFormat.format(anInterface.getLastupdatetime());
                                            PdfParagraph modifyPara = new PdfParagraph(contents, CONTENT_SIZE, false);
                                            modifyPara.setAlignment(Element.ALIGN_RIGHT);
                                            section.add(modifyPara);
                                            String infoStr = "";
                                            if (anInterface.getProtocol().equals(HYConst.Protocol.WEBSERVICE.getName())) {
                                                infoStr = "请求类型:" + anInterface.getProtocol() + "\n请求地址:" + anInterface.getUrl() + "\n方法名称:" + anInterface.getInterfaceWS().getMethodname() + "\n命名空间:" + anInterface.getInterfaceWS().getTargetnamespace() + "\n数据类型:" + anInterface.getContenttype() + "\n响应类型:" + anInterface.getDatatype() + "\n描述:" + anInterface.getDescription();
                                            } else {
                                                infoStr = "请求类型:" + anInterface.getProtocol() + "\n请求地址:" + anInterface.getUrl() + "\n请求方式:" + anInterface.getRequestmethod() + "\n数据类型:" + anInterface.getContenttype() + "\n响应类型:" + anInterface.getDatatype() + "\n描述:" + anInterface.getDescription();
                                            }
                                            PdfParagraph interfaceInfoPara = new PdfParagraph(infoStr, CONTENT_SIZE, false);
                                            section.add(interfaceInfoPara);
                                            /*发送数据(表格)*/
                                            PdfParagraph requestPara = new PdfParagraph("发送数据\n", CONTENT_SIZE, true);
                                            section.add(requestPara);
                                            section.add(new PdfParagraph("请求头(Header)\n\n", CONTENT_SIZE, false));
                                            JSONArray headerArray = JSONArray.parseArray(anInterface.getRequestheaders());
                                            processDataTable(section, headerArray);
                                            section.add(new PdfParagraph("请求参数(Body)\n\n", CONTENT_SIZE, false));
                                            JSONArray requestArray = JSONArray.parseArray(anInterface.getRequestargs());
                                            processDataTable(section, requestArray);
                                            /*响应数据(表格)*/
                                            PdfParagraph responsePara = new PdfParagraph("响应数据\n\n", CONTENT_SIZE, true);
                                            section.add(responsePara);
                                            JSONArray responseArray = JSONArray.parseArray(anInterface.getResponseargs());
                                            processDataTable(section, responseArray);
                                        }
                                    }
                                    document.add(chapter);
                                }
                            }
                            if (i == 0) {
                                document.add(chapter);
                            }
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            document.close();
        } catch (com.lowagie.text.DocumentException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理参数数据表格
     *
     * @param section
     * @param array
     * @throws DocumentException
     */
    private void processDataTable(Section section, JSONArray array) throws DocumentException {
        Object[] array1 = array.toArray();
        String[] headers = {"参数名称", "是否必须", "数据类型", "描述"};
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(25 * headers.length);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        // 产生表格标题行
        for (int v = 0; v < headers.length; v++) {
            PdfPCell cell = new PdfPCell(new PdfParagraph(headers[v], CONTENT_SIZE, false));
            cell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            cell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            cell.setBackgroundColor(Color.lightGray);
            cell.setFixedHeight(HEAD_ROW_HEIGHT);
            table.addCell(cell);
        }
        buildCell(array1, table);
        section.add(table);
        /*处理引用类型参数属性*/
        processReference(section, array1, headers);
    }

    /**
     * 处理引用类型属性
     *
     * @param section
     * @param array1
     * @param headers
     * @throws DocumentException
     */
    private void processReference(Section section, Object[] array1, String[] headers) throws DocumentException {
        for (Object obj : array1) {
            Map<String, Object> res = (Map) obj;
            if (res.containsKey("paramType")) {
                String paramType = res.get("paramType") + "";
                /*处理参数类型为数组或对象的，再组一个表格列该对象属性*/
                if (paramType.equals("array") || paramType.equals("object")) {
                    String paramName = res.get("paramName") + "";
                    String properties = res.get("defaultValue") + "";
                    JSONArray proArray = JSONArray.parseArray(properties);
                    if (proArray != null) {
                        PdfPTable pTable = buildReferenceTable(headers, paramName);
                        Object[] proArrays = proArray.toArray();
                        buildCell(proArrays, pTable);
                        section.add(pTable);
                        processReference(section, proArrays, headers);
                    }
                }
            }
        }
    }

    /**
     * 构建引用类型属性表格
     *
     * @param headers
     * @param paramName
     * @return
     */
    private PdfPTable buildReferenceTable(String[] headers, String paramName) {
        PdfPTable pTable = new PdfPTable(headers.length);
        pTable.setWidthPercentage(25 * headers.length);
        pTable.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell pHeader = new PdfPCell(new PdfParagraph("参数" + paramName + "属性列表", CONTENT_SIZE, false));
        pHeader.setHorizontalAlignment(Cell.ALIGN_CENTER);
        pHeader.setVerticalAlignment(Cell.ALIGN_MIDDLE);
        pHeader.setFixedHeight(ROW_HEIGHT);
        pHeader.setColspan(4);
        pTable.addCell(pHeader);
        return pTable;
    }

    /**
     * 构建表格中的cell
     *
     * @param array1 参数集合
     * @param table  表格对象
     */
    private void buildCell(Object[] array1, PdfPTable table) {
        for (Object obj : array1) {
            Map<String, Object> res = (Map) obj;
            PdfPCell nameCell = new PdfPCell(new PdfParagraph(res.containsKey("paramName") ? res.get("paramName") + "" : ""));
            nameCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            nameCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            nameCell.setFixedHeight(ROW_HEIGHT);
            table.addCell(nameCell);
            PdfPCell requiredCell = new PdfPCell(new PdfParagraph(res.containsKey("isRequired") ? res.get("isRequired") + "" : ""));
            requiredCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            requiredCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(requiredCell);
            PdfPCell typeCell = new PdfPCell(new PdfParagraph(res.containsKey("paramType") ? res.get("paramType") + "" : ""));
            typeCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            typeCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(typeCell);
            PdfPCell descCell = new PdfPCell(new PdfParagraph(res.containsKey("paramDesc") ? res.get("paramDesc") + "" : ""));
            descCell.setHorizontalAlignment(Cell.ALIGN_CENTER);
            descCell.setVerticalAlignment(Cell.ALIGN_MIDDLE);
            table.addCell(descCell);
        }
    }
}

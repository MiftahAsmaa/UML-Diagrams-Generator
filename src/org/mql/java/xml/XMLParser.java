package org.mql.java.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;
import org.mql.java.models.RelationInfo;
import org.mql.java.models.RelationInfo.RelationType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class XMLParser {

    public XMLParser(String xmlFilePath) {
        parseProjectXml(xmlFilePath);
    }

    public static ProjectInfo parseProjectXml(String xmlFilePath) {
        try {
            File inputFile = new File(xmlFilePath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(inputFile);
            document.getDocumentElement().normalize();

            Element projectElement = document.getDocumentElement();

            String projectName = projectElement.getElementsByTagName("name").item(0).getTextContent();

            List<PackageInfo> packages = new ArrayList<>();
            ProjectInfo project = new ProjectInfo(projectName, packages);

            NodeList packageNodes = projectElement.getElementsByTagName("package");
            for (int i = 0; i < packageNodes.getLength(); i++) {
                Element packageElement = (Element) packageNodes.item(i);
                PackageInfo packageInfo = parsePackage(document, packageElement);
                packages.add(packageInfo);
            }

            return project;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static PackageInfo parsePackage(Document document, Element packageElement) {
        String name = packageElement.getAttribute("name");

        PackageInfo packageInfo = new PackageInfo(name);
        List<ClassInfo> allFiles = new ArrayList<>();
        List<ClassInfo> classes = new ArrayList<>();
        String[] fileTypes = {"Class", "Interface", "Annotation", "Enum"};
        for (String type : fileTypes) {
            NodeList classNodes = packageElement.getElementsByTagName(type);
            for (int i = 0; i < classNodes.getLength(); i++) {
                Element classElement = (Element) classNodes.item(i);
                ClassInfo classInfo = parseClass(document, classElement);
                classInfo.setClassType(type);
                classes.add(classInfo);
                allFiles.add(classInfo);
            }
        }
        packageInfo.setClasses(classes);

        return packageInfo;
    }

    private static ClassInfo parseClass(Document document, Element classElement) {
        String name = classElement.getAttribute("name");
        String type = classElement.getAttribute("type");

        List<FieldInfo> fields = parseFields(document, classElement);
        List<MethodInfo> methods = parseMethods(document, classElement);
        List<RelationInfo> relations = parseRelations(document, classElement);

        ClassInfo classInfo = new ClassInfo(name, type, fields, methods);
        classInfo.setRelations(relations);
        classInfo.setClassType(type);

        List<ClassInfo> relatedClasses = parseRelatedClasses(document, classElement);
        classInfo.setRelatedClasses(relatedClasses);

        return classInfo;
    }

    private static List<FieldInfo> parseFields(Document document, Element classElement) {
        List<FieldInfo> fields = new ArrayList<>();
        NodeList fieldNodes = classElement.getElementsByTagName("field");
        for (int i = 0; i < fieldNodes.getLength(); i++) {
            Element fieldElement = (Element) fieldNodes.item(i);
            FieldInfo field = parseField(document, fieldElement);
            fields.add(field);
        }
        return fields;
    }

    private static List<MethodInfo> parseMethods(Document document, Element classElement) {
        List<MethodInfo> methods = new ArrayList<>();
        NodeList methodNodes = classElement.getElementsByTagName("method");
        for (int i = 0; i < methodNodes.getLength(); i++) {
            Element methodElement = (Element) methodNodes.item(i);
            MethodInfo method = parseMethod(document, methodElement);
            methods.add(method);
        }
        return methods;
    }

    private static List<RelationInfo> parseRelations(Document document, Element classElement) {
        List<RelationInfo> relations = new ArrayList<>();
        NodeList relationNodes = classElement.getElementsByTagName("relation");
        for (int i = 0; i < relationNodes.getLength(); i++) {
            Element relationElement = (Element) relationNodes.item(i);
            RelationInfo relation = parseRelation(document, relationElement);
            relations.add(relation);
        }
        return relations;
    }

    private static List<ClassInfo> parseRelatedClasses(Document document, Element classElement) {
        List<ClassInfo> relatedClasses = new ArrayList<>();
        NodeList relatedClassNodes = classElement.getElementsByTagName("relatedClass");
        for (int i = 0; i < relatedClassNodes.getLength(); i++) {
            Element relatedClassElement = (Element) relatedClassNodes.item(i);
            String relatedClassName = relatedClassElement.getAttribute("name");
            ClassInfo relatedClass = new ClassInfo(relatedClassName, "", Collections.emptyList(), Collections.emptyList());
            relatedClasses.add(relatedClass);
        }
        return relatedClasses;
    }

    private static FieldInfo parseField(Document document, Element fieldElement) {
        String name = fieldElement.getAttribute("name");
        String type = fieldElement.getAttribute("type");
        String modifier = fieldElement.getAttribute("modifier");
        return new FieldInfo(name, modifier , type);
    }

    private static MethodInfo parseMethod(Document document, Element methodElement) {
        String name = methodElement.getAttribute("name");
        String type = methodElement.getAttribute("returnType");
        String modifier = methodElement.getAttribute("modifier");
       List<String> params = parseParameters(document, methodElement);
        return new MethodInfo(name, modifier, type,params);

    }

    private static List<String> parseParameters(Document document, Element methodElement) {
        List<String> params = new ArrayList<>();
        NodeList paramNodes = methodElement.getElementsByTagName("parameter");
        for (int i = 0; i < paramNodes.getLength(); i++) {
            Element paramNode = (Element) paramNodes.item(i);
            String paramType = paramNode.getAttribute("type");
            params.add(paramType);
        }
        return params;
    }

    private static RelationInfo parseRelation(Document document, Element relationElement) {
        String sourceClass = relationElement.getAttribute("sourceClass");
        String targetClass = relationElement.getAttribute("targetClass");
        String typeString = relationElement.getAttribute("type");
        RelationType type = RelationType.valueOf(typeString);
        return new RelationInfo(type, sourceClass, targetClass);
    }
}

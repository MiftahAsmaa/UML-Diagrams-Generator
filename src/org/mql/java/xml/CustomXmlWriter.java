package org.mql.java.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.mql.java.models.ClassInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CustomXmlWriter {
/*
    public static void writeDataToFile(List<ProjectInfo> projects, String filePath) {
        Element rootElement = new Element("projects");

        Document document = new Document(rootElement);

        addProjectsToElement(projects, rootElement);

        // Écrire le document XML dans un fichier
        writeDocumentToFile(document, filePath);
    }

    private static void addProjectsToElement(List<ProjectInfo> projects, Element parentElement) {
        for (ProjectInfo project : projects) {
            Element projectElement = new Element("project");

            // Ajouter des éléments pour représenter les propriétés du projet
            Element projectNameElement = new Element("projectName");
            projectNameElement.setText(project.getProjectName());
            projectElement.addContent(projectNameElement);

            // Ajouter des éléments pour représenter les informations du package
            PackageInfo packageInfo = project.getPackages().get(0); // Supposons un seul package
            addPackageToElement(packageInfo, projectElement);

            // Ajouter l'élément du projet à l'élément parent
            parentElement.addContent(projectElement);
        }
    }

    private static void addPackageToElement(PackageInfo packageInfo, Element parentElement) {
        Element packageElement = new Element("package");

        // Ajouter des éléments pour représenter les propriétés du package
        Element packageNameElement = new Element("packageName");
        packageNameElement.setText(packageInfo.getpackageName());
        packageElement.addContent(packageNameElement);

        // Ajouter des éléments pour représenter les informations de la classe
        ClassInfo classInfo = packageInfo.getClasses().get(0); // Supposons une seule classe
        addClassToElement(classInfo, packageElement);

        // Ajouter l'élément du package à l'élément parent
        parentElement.addContent(packageElement);
    }

    private static void addClassToElement(ClassInfo classInfo, Element parentElement) {
        Element classElement = new Element("class");

        // Ajouter des éléments pour représenter les propriétés de la classe
        Element classNameElement = new Element("className");
        classNameElement.setText(classInfo.getClassName());
        classElement.addContent(classNameElement);

        // Ajouter des éléments pour représenter les champs de la classe
        addFieldsToElement(classInfo.getFields(), classElement);

        // Ajouter l'élément de la classe à l'élément parent
        parentElement.addContent(classElement);
    }

    private static void addFieldsToElement(List<FieldInfo> fields, Element parentElement) {
        for (FieldInfo fieldInfo : fields) {
            Element fieldElement = new Element("field");

            // Ajouter des éléments pour représenter les propriétés du champ
            Element fieldNameElement = new Element("fieldName");
            fieldNameElement.setText(fieldInfo.getFieldName());
            fieldElement.addContent(fieldNameElement);

            Element fieldTypeElement = new Element("fieldType");
            fieldTypeElement.setText(fieldInfo.getFieldType());
            fieldElement.addContent(fieldTypeElement);

            // Ajouter l'élément du champ à l'élément parent
            parentElement.addContent(fieldElement);
        }
    }

    private static void writeDocumentToFile(Document document, String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
            xmlOutputter.output(document, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}

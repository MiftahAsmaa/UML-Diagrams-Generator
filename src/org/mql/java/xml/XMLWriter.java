package org.mql.java.xml;
import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.mql.java.models.ClassInfo;
import org.mql.java.models.ConstructorInfo;
import org.mql.java.models.FieldInfo;
import org.mql.java.models.MethodInfo;
import org.mql.java.models.PackageInfo;
import org.mql.java.models.ProjectInfo;
import org.mql.java.models.RelationInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLWriter {

    public static void writeProjectToXML(ProjectInfo project, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("project");
            doc.appendChild(rootElement);
            Element projectName = doc.createElement("name");
            projectName.appendChild(doc.createTextNode(project.getProjectName()));
            rootElement.appendChild(projectName);
            createPackageElement(doc, rootElement, project.getPackages());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
            System.out.println("File saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPackageElement(Document doc, Element parentElement, List<PackageInfo> packages) {
        Element packagesElement = doc.createElement("packages");
        parentElement.appendChild(packagesElement);

        for (PackageInfo packageInfo : packages) {
            Element packageElement = doc.createElement("package");
            packageElement.setAttribute("name", packageInfo.getPackageName());
            createClassElement(doc, packageElement, packageInfo.getClasses());
            packagesElement.appendChild(packageElement);
        }
    }

    private static void createClassElement(Document doc, Element parentElement, List<ClassInfo> classes) {
        for (ClassInfo classInfo : classes) {
            Element classElement;
            if ("Class".equals(classInfo.getClassType())) {
                classElement = doc.createElement("Class");
            } else if ("Interface".equals(classInfo.getClassType())) {
                classElement = doc.createElement("Interface");
            }else if ("Enum".equals(classInfo.getClassType())) {
                classElement = doc.createElement("Enum");
            }else if("Annotation".equals(classInfo.getClassType())) {
                classElement = doc.createElement("Annotation");
            }else {
                continue;
            }
            
            String className=classInfo.getClassName().substring(classInfo.getClassName().lastIndexOf('.') + 1);
            classElement.setAttribute("name", className);
            classElement.setAttribute("type", classInfo.getClassType());

            List<ConstructorInfo> constructors = classInfo.getConstructors();
            if (constructors != null && !constructors.isEmpty()) {
            	Element constructorsElement = doc.createElement("constructors");
                for (ConstructorInfo constructor: constructors) {
                    Element constructorElement = doc.createElement("constuctor");
                    constructorElement.setAttribute("name", constructor.getconstructorName());
                    constructorElement.setAttribute("modifier", constructor.getconstructorModifier());

                    List<String> parameterTypes = constructor.getParameters();
                    if (parameterTypes != null && !parameterTypes.isEmpty()) {
                        Element parametersElement = doc.createElement("parameters");
                        for (String parameterType : parameterTypes) {
                            Element parameterElement = doc.createElement("parameter");
                            parameterElement.setAttribute("type", parameterType);
                            parametersElement.appendChild(parameterElement);
                        }
                        constructorElement.appendChild(parametersElement);
                    }

                    constructorsElement.appendChild(constructorElement);
                }
                classElement.appendChild(constructorsElement);
            }
            
            List<FieldInfo> fields = classInfo.getFields();
            if (fields != null && !fields.isEmpty()) {
                Element fieldsElement = doc.createElement("fields");
                for (FieldInfo field : fields) {
                    Element fieldElement = doc.createElement("field");
                    fieldElement.setAttribute("name", field.getFieldName());
                    fieldElement.setAttribute("modifier", field.getFieldModifier());
                    fieldElement.setAttribute("type", field.getFieldType());
                    fieldsElement.appendChild(fieldElement);
                }
                classElement.appendChild(fieldsElement);
            }

            List<MethodInfo> methods = classInfo.getMethods();
            if (methods != null && !methods.isEmpty()) {
                Element methodsElement = doc.createElement("methods");
                for (MethodInfo method : methods) {
                    Element methodElement = doc.createElement("method");
                    methodElement.setAttribute("name", method.getMethodName());
                    methodElement.setAttribute("modifier", method.getMethodModifier());
                    methodElement.setAttribute("returnType", method.getReturnType());

                    List<String> parameterTypes = method.getParameters();
                    if (parameterTypes != null && !parameterTypes.isEmpty()) {
                        Element parametersElement = doc.createElement("parameters");
                        for (String parameterType : parameterTypes) {
                            Element parameterElement = doc.createElement("parameter");
                            parameterElement.setAttribute("type", parameterType);
                            parametersElement.appendChild(parameterElement);
                        }
                        methodElement.appendChild(parametersElement);
                    }

                    methodsElement.appendChild(methodElement);
                }
                classElement.appendChild(methodsElement);
            }
            
            List<RelationInfo> relations = classInfo.getRelations();
            if (relations != null && !relations.isEmpty()) {
                Element relationsElement = doc.createElement("Relations");
                for (RelationInfo relationInfo : relations) {
                    Element relationElement = doc.createElement("Realation");
                    relationElement.setAttribute("type", relationInfo.getType().name());
                    relationElement.setAttribute("sourceClass", escapeXML(relationInfo.getSourceClass()));
                    relationElement.setAttribute("targetClass", escapeXML(relationInfo.getTargetClass()));
                    relationsElement.appendChild(relationElement);
                }
                classElement.appendChild(relationsElement);
            }

            parentElement.appendChild(classElement);
        }
    }

	private static String escapeXML(String value) {
    value = value.replace("&", "&amp;");
    value = value.replace("<", "&lt;");
    value = value.replace(">", "&gt;");
    return value;
}
}


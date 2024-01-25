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

public class XMIWriter {

    public static void writeProjectToXMI(ProjectInfo project, String filePath) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();
            Element xmiRoot = doc.createElement("xmi:XMI");
            xmiRoot.setAttribute("xmlns:xmi", "http://www.omg.org/XMI");
            xmiRoot.setAttribute("xmlns:uml", "http://www.omg.org/spec/UML/20090901");

            doc.appendChild(xmiRoot);
            Element projectElement = doc.createElement("uml:Project");
            xmiRoot.appendChild(projectElement);
            Element projectName = doc.createElement("uml:Name");
            projectName.appendChild(doc.createTextNode(project.getProjectName()));
            projectElement.appendChild(projectName);
            Element packages = doc.createElement("uml:Packages");
            projectElement.appendChild(packages);

            createPackageElement(doc, packages, project.getPackages());

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filePath));

            transformer.transform(source, result);
            System.out.println("XMI file saved!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPackageElement(Document doc, Element parentElement, List<PackageInfo> packages) {
        for (PackageInfo packageInfo : packages) {
            Element packageElement = doc.createElement("uml:Package");
            packageElement.setAttribute("name", packageInfo.getPackageName());

            createClassElement(doc, packageElement, packageInfo.getClasses());

            parentElement.appendChild(packageElement);
        }
    }

    private static void createClassElement(Document doc, Element parentElement, List<ClassInfo> classes) {
        for (ClassInfo classInfo : classes) {
            Element classElement;
            if ("Class".equals(classInfo.getClassType())) {
                classElement = doc.createElement("uml:Class");
            } else if ("Interface".equals(classInfo.getClassType())) {
                classElement = doc.createElement("uml:Interface");
            }else if ("Enum".equals(classInfo.getClassType())) {
                classElement = doc.createElement("uml:Enum");
            }else if("Annotation".equals(classInfo.getClassType())) {
                classElement = doc.createElement("uml:Annotation");
            }else {
                continue;
            }

            String className=classInfo.getClassName().substring(classInfo.getClassName().lastIndexOf('.') + 1);
            classElement.setAttribute("name", className);
            List<ConstructorInfo> constructors = classInfo.getConstructors();
            if (constructors != null && !constructors.isEmpty()) {
            	Element constructorsElement = doc.createElement("uml:Constructors");
                for (ConstructorInfo constructor: constructors) {
                    Element constructorElement = doc.createElement("uml:Constuctor");
                    constructorElement.setAttribute("name", constructor.getconstructorName());
                    constructorElement.setAttribute("modifier", constructor.getconstructorModifier());

                    List<String> parameterTypes = constructor.getParameters();
                    if (parameterTypes != null && !parameterTypes.isEmpty()) {
                        Element parametersElement = doc.createElement("uml:Parameters");
                        for (String parameterType : parameterTypes) {
                            Element parameterElement = doc.createElement("uml:Parameter");
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
                for (FieldInfo field : fields) {
                    Element fieldElement = doc.createElement("uml:Attribute");
                    fieldElement.setAttribute("name", field.getFieldName());
                    fieldElement.setAttribute("type", field.getFieldType());
                    classElement.appendChild(fieldElement);
                }
            }

            List<MethodInfo> methods = classInfo.getMethods();
            if (methods != null && !methods.isEmpty()) {
                for (MethodInfo method : methods) {
                    Element methodElement = doc.createElement("uml:Operation");
                    methodElement.setAttribute("name", method.getMethodName());
                    methodElement.setAttribute("returnType", method.getReturnType());

                    List<String> parameterTypes = method.getParameters();
                    if (parameterTypes != null && !parameterTypes.isEmpty()) {
                        for (String parameterType : parameterTypes) {
                            Element parameterElement = doc.createElement("uml:Parameter");
                            parameterElement.setAttribute("name", "param");
                            parameterElement.setAttribute("type", parameterType);
                            methodElement.appendChild(parameterElement);
                        }
                    }

                    classElement.appendChild(methodElement);
                }
            }
            
            List<RelationInfo> relations = classInfo.getRelations();
            if (relations != null && !relations.isEmpty()) {
                Element relationsElement = doc.createElement("uml:Relations");
                for (RelationInfo relationInfo : relations) {
                    Element relationElement = doc.createElement("uml:Realation");
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

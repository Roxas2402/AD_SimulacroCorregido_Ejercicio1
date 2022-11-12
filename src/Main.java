import models.Animal;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static ArrayList<Animal> animalesList;

    //TODO: Hacer la clase serializable
    public static void main(String[] args) {
        animalesList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            opcion = menu(scanner);
            switch (opcion) {
                case 1:
                    crearAnimal(scanner);
                    break;
                case 2:
                    try {
                        escribirLista(animalesList);
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 3:
                    try {
                        descargarBinario(animalesList);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 4:
                    try {
                        escribirXML(scanner, animalesList);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (TransformerException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    try {
                        leerXML(animalesList);
                    } catch (ParserConfigurationException e) {
                        throw new RuntimeException(e);
                    } catch (SAXException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 6:
                    break;
            }
        } while (opcion != 6);
    }

    private static void leerXML(ArrayList<Animal> animalesList) throws ParserConfigurationException, SAXException, IOException {
        //DOM
        File file = new File("archivo.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);
        document.getDocumentElement().normalize();

        NodeList nodos = document.getElementsByTagName("animal");

        for (int i = 0; i < nodos.getLength(); i++) {
            Node nodo = nodos.item(i);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element animal = (Element) nodo;
                String especie = animal.getElementsByTagName("especie").item(0).getTextContent();
                String raza = animal.getElementsByTagName("raza").item(0).getTextContent();
                String color = animal.getElementsByTagName("color").item(0).getTextContent();
                int edad = Integer.parseInt(animal.getElementsByTagName("edad").item(0).getTextContent());

                Animal animal1 = new Animal(especie, raza, edad, color);
                System.out.println(animal1);

                for (Animal a : animalesList) {
                    System.out.println(a.toString());
                }

            }
        }

    }

    private static void escribirXML(Scanner scanner, ArrayList<Animal> animalesList) throws ParserConfigurationException, TransformerException {
        //DOM
        String file = "archivo.xml";

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.newDocument();

        Element raiz = doc.createElement("Animales");
        doc.appendChild(raiz);
        for (Animal a : animalesList) {
            Element animal = doc.createElement("animal");
            raiz.appendChild(animal);

            Element especie = doc.createElement("especie");
            especie.setTextContent(a.getEspecie());
            animal.appendChild(especie);

            Element raza = doc.createElement("raza");
            raza.setTextContent(a.getRaza());
            animal.appendChild(raza);

            Element edad = doc.createElement("edad");
            edad.setTextContent(String.valueOf(a.getEdad()));
            animal.appendChild(edad);

            Element color = doc.createElement("color");
            color.setTextContent(a.getColor());
            animal.appendChild(color);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer t = tf.newTransformer();
        DOMSource ds = new DOMSource(doc);

        t.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult sr = new StreamResult(new File(file));
        t.transform(ds, sr);

    }

    private static void descargarBinario(ArrayList<Animal> animalesList) throws IOException, ClassNotFoundException {
        File file = new File("binario.dat");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        int i = 1;

        try {
            while (true) {
                animalesList.add((Animal) ois.readObject());
            }
        } catch (EOFException eo) {
            System.out.println("FIN DE LECTURA.");
        }
        for (Animal a : animalesList) {
            System.out.println(a.toString());
        }
    }

    private static void escribirLista(ArrayList<Animal> animalesList) throws IOException {
        File file = new File("binario.dat");
        FileOutputStream fos = new FileOutputStream(file, true);
        ObjectOutputStream ous = new ObjectOutputStream(fos);

        for (Animal a : animalesList) {
            ous.writeObject(a);
        }

        ous.close();

        System.out.println("Hecho");
    }

    private static void crearAnimal(Scanner scanner) {
        String especie;
        String raza;
        int edad;
        String color;

        System.out.println("Dime la especie del animal");
        especie = scanner.nextLine();
        System.out.println("Dime la raza del animal");
        raza = scanner.nextLine();
        System.out.println("Dime el color del animal");
        color = scanner.nextLine();
        System.out.println("Dime la edad del animal");
        edad = scanner.nextInt();
        Animal animal = new Animal(especie, raza, edad, color);
        animalesList.add(animal);
        System.out.println("Nuevo " + animal.getEspecie() + " creado con éxito.");
    }

    private static int menu(Scanner scanner) {
        int opcion;
        do {
            System.out.println("1: Crear animal");
            System.out.println("2: Escribir en fichero binario");
            System.out.println("3: Cargar de fichero binario");
            System.out.println("4: Escribir en fichero XML");
            System.out.println("5: Cargar de fichero XML");
            System.out.println("6: Salir");
            try {
                opcion = scanner.nextInt();
            } catch (InputMismatchException exception) {
                opcion = 0;
                System.out.println("Error");
                scanner.nextLine();
            }
        } while (opcion < 1 || opcion > 6);
        scanner.nextLine();
        return opcion;
    }
}
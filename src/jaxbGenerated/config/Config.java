//
// Diese Datei wurde mit der JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 generiert 
// Siehe <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Änderungen an dieser Datei gehen bei einer Neukompilierung des Quellschemas verloren. 
// Generiert: 2016.06.30 um 08:16:07 PM CEST 
//


package jaxbGenerated.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addr">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="setze" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="cv" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                           &lt;attribute name="wert" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="cv" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="startbit" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="stopbit" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="byteorder" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="format" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="kategorie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subkategorie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="werte">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="auswahl" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="wert" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="einheit" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="faktor" type="{http://www.w3.org/2001/XMLSchema}double" />
 *                 &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "addr",
    "format",
    "name",
    "beschreibung",
    "kategorie",
    "subkategorie",
    "werte"
})
@XmlRootElement(name = "config")
public class Config extends ConfigBase
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected Config.Addr addr;
    @XmlElement(required = true)
    protected String format;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected String beschreibung;
    @XmlElement(required = true)
    protected String kategorie;
    @XmlElement(required = true)
    protected String subkategorie;
    @XmlElement(required = true)
    protected Config.Werte werte;

    /**
     * Ruft den Wert der addr-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Config.Addr }
     *     
     */
    public Config.Addr getAddr() {
        return addr;
    }

    /**
     * Legt den Wert der addr-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Config.Addr }
     *     
     */
    public void setAddr(Config.Addr value) {
        this.addr = value;
    }

    /**
     * Ruft den Wert der format-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Legt den Wert der format-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Ruft den Wert der name-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Legt den Wert der name-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Ruft den Wert der beschreibung-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeschreibung() {
        return beschreibung;
    }

    /**
     * Legt den Wert der beschreibung-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeschreibung(String value) {
        this.beschreibung = value;
    }

    /**
     * Ruft den Wert der kategorie-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKategorie() {
        return kategorie;
    }

    /**
     * Legt den Wert der kategorie-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKategorie(String value) {
        this.kategorie = value;
    }

    /**
     * Ruft den Wert der subkategorie-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubkategorie() {
        return subkategorie;
    }

    /**
     * Legt den Wert der subkategorie-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubkategorie(String value) {
        this.subkategorie = value;
    }

    /**
     * Ruft den Wert der werte-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Config.Werte }
     *     
     */
    public Config.Werte getWerte() {
        return werte;
    }

    /**
     * Legt den Wert der werte-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Config.Werte }
     *     
     */
    public void setWerte(Config.Werte value) {
        this.werte = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="setze" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="cv" type="{http://www.w3.org/2001/XMLSchema}int" />
     *                 &lt;attribute name="wert" type="{http://www.w3.org/2001/XMLSchema}int" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="cv" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="startbit" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="stopbit" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="byteorder" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "setze"
    })
    public static class Addr
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Config.Addr.Setze> setze;
        @XmlAttribute(name = "cv")
        protected Integer cv;
        @XmlAttribute(name = "startbit")
        protected Integer startbit;
        @XmlAttribute(name = "stopbit")
        protected Integer stopbit;
        @XmlAttribute(name = "length")
        protected Integer length;
        @XmlAttribute(name = "byteorder")
        protected String byteorder;

        /**
         * Gets the value of the setze property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the setze property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSetze().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Config.Addr.Setze }
         * 
         * 
         */
        public List<Config.Addr.Setze> getSetze() {
            if (setze == null) {
                setze = new ArrayList<>();
            }
            return this.setze;
        }

        /**
         * Ruft den Wert der cv-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCv() {
            return cv;
        }

        /**
         * Legt den Wert der cv-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCv(Integer value) {
            this.cv = value;
        }

        /**
         * Ruft den Wert der startbit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getStartbit() {
            return startbit;
        }

        /**
         * Legt den Wert der startbit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setStartbit(Integer value) {
            this.startbit = value;
        }

        /**
         * Ruft den Wert der stopbit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getStopbit() {
            return stopbit;
        }

        /**
         * Legt den Wert der stopbit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setStopbit(Integer value) {
            this.stopbit = value;
        }

        /**
         * Ruft den Wert der length-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getLength() {
            return length;
        }

        /**
         * Legt den Wert der length-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setLength(Integer value) {
            this.length = value;
        }

        /**
         * Ruft den Wert der byteorder-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getByteorder() {
            return byteorder;
        }

        /**
         * Legt den Wert der byteorder-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setByteorder(String value) {
            this.byteorder = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="cv" type="{http://www.w3.org/2001/XMLSchema}int" />
         *       &lt;attribute name="wert" type="{http://www.w3.org/2001/XMLSchema}int" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Setze
            implements Serializable
        {

            private final static long serialVersionUID = 1L;
            @XmlAttribute(name = "cv")
            protected Integer cv;
            @XmlAttribute(name = "wert")
            protected Integer wert;

            /**
             * Ruft den Wert der cv-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getCv() {
                return cv;
            }

            /**
             * Legt den Wert der cv-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setCv(Integer value) {
                this.cv = value;
            }

            /**
             * Ruft den Wert der wert-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link Integer }
             *     
             */
            public Integer getWert() {
                return wert;
            }

            /**
             * Legt den Wert der wert-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link Integer }
             *     
             */
            public void setWert(Integer value) {
                this.wert = value;
            }

        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="auswahl" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="wert" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="min" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="einheit" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="faktor" type="{http://www.w3.org/2001/XMLSchema}double" />
     *       &lt;attribute name="max" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "auswahl"
    })
    public static class Werte
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Config.Werte.Auswahl> auswahl;
        @XmlAttribute(name = "min")
        protected Integer min;
        @XmlAttribute(name = "einheit")
        protected String einheit;
        @XmlAttribute(name = "faktor")
        protected Double faktor;
        @XmlAttribute(name = "max")
        protected Integer max;

        /**
         * Gets the value of the auswahl property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the auswahl property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getAuswahl().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Config.Werte.Auswahl }
         * 
         * 
         */
        public List<Config.Werte.Auswahl> getAuswahl() {
            if (auswahl == null) {
                auswahl = new ArrayList<>();
            }
            return this.auswahl;
        }

        /**
         * Ruft den Wert der min-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getMin() {
            return min;
        }

        /**
         * Legt den Wert der min-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setMin(Integer value) {
            this.min = value;
        }

        /**
         * Ruft den Wert der einheit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEinheit() {
            return einheit;
        }

        /**
         * Legt den Wert der einheit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEinheit(String value) {
            this.einheit = value;
        }

        /**
         * Ruft den Wert der faktor-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getFaktor() {
            return faktor;
        }

        /**
         * Legt den Wert der faktor-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setFaktor(Double value) {
            this.faktor = value;
        }

        /**
         * Ruft den Wert der max-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getMax() {
            return max;
        }

        /**
         * Legt den Wert der max-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setMax(Integer value) {
            this.max = value;
        }


        /**
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="wert" type="{http://www.w3.org/2001/XMLSchema}int"/>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="beschreibung" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "wert",
            "name",
            "beschreibung"
        })
        public static class Auswahl
            implements Serializable
        {

            private final static long serialVersionUID = 1L;
            protected int wert;
            @XmlElement(required = true)
            protected String name;
            @XmlElement(required = true)
            protected String beschreibung;

            /**
             * Ruft den Wert der wert-Eigenschaft ab.
             * 
             */
            public int getWert() {
                return wert;
            }

            /**
             * Legt den Wert der wert-Eigenschaft fest.
             * 
             */
            public void setWert(int value) {
                this.wert = value;
            }

            /**
             * Ruft den Wert der name-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Legt den Wert der name-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Ruft den Wert der beschreibung-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBeschreibung() {
                return beschreibung;
            }

            /**
             * Legt den Wert der beschreibung-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBeschreibung(String value) {
                this.beschreibung = value;
            }

        }

    }

}

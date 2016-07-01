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
 *         &lt;element ref="{}config" maxOccurs="unbounded"/>
 *         &lt;element name="repeater" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{}config" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="stop" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="cvoffset" type="{http://www.w3.org/2001/XMLSchema}int" />
 *                 &lt;attribute name="cverhoehung" type="{http://www.w3.org/2001/XMLSchema}int" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "config",
    "repeater"
})
@XmlRootElement(name = "lokdekoder")
public class Lokdekoder
    implements Serializable
{

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected List<Config> config;
    @XmlElement(required = true)
    protected List<Lokdekoder.Repeater> repeater;
    @XmlAttribute(name = "id")
    protected Integer id;
    @XmlAttribute(name = "name")
    protected String name;

    /**
     * Gets the value of the config property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the config property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getConfig().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Config }
     * 
     * 
     */
    public List<Config> getConfig() {
        if (config == null) {
            config = new ArrayList<Config>();
        }
        return this.config;
    }

    /**
     * Gets the value of the repeater property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the repeater property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRepeater().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Lokdekoder.Repeater }
     * 
     * 
     */
    public List<Lokdekoder.Repeater> getRepeater() {
        if (repeater == null) {
            repeater = new ArrayList<Lokdekoder.Repeater>();
        }
        return this.repeater;
    }

    /**
     * Ruft den Wert der id-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Legt den Wert der id-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
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
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{}config" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *       &lt;attribute name="start" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="stop" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="cvoffset" type="{http://www.w3.org/2001/XMLSchema}int" />
     *       &lt;attribute name="cverhoehung" type="{http://www.w3.org/2001/XMLSchema}int" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "config"
    })
    public static class Repeater
        implements Serializable
    {

        private final static long serialVersionUID = 1L;
        @XmlElement(required = true)
        protected List<Config> config;
        @XmlAttribute(name = "start")
        protected Integer start;
        @XmlAttribute(name = "stop")
        protected Integer stop;
        @XmlAttribute(name = "cvoffset")
        protected Integer cvoffset;
        @XmlAttribute(name = "cverhoehung")
        protected Integer cverhoehung;

        /**
         * Gets the value of the config property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the config property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConfig().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Config }
         * 
         * 
         */
        public List<Config> getConfig() {
            if (config == null) {
                config = new ArrayList<Config>();
            }
            return this.config;
        }

        /**
         * Ruft den Wert der start-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getStart() {
            return start;
        }

        /**
         * Legt den Wert der start-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setStart(Integer value) {
            this.start = value;
        }

        /**
         * Ruft den Wert der stop-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getStop() {
            return stop;
        }

        /**
         * Legt den Wert der stop-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setStop(Integer value) {
            this.stop = value;
        }

        /**
         * Ruft den Wert der cvoffset-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCvoffset() {
            return cvoffset;
        }

        /**
         * Legt den Wert der cvoffset-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCvoffset(Integer value) {
            this.cvoffset = value;
        }

        /**
         * Ruft den Wert der cverhoehung-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Integer }
         *     
         */
        public Integer getCverhoehung() {
            return cverhoehung;
        }

        /**
         * Legt den Wert der cverhoehung-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Integer }
         *     
         */
        public void setCverhoehung(Integer value) {
            this.cverhoehung = value;
        }

    }

}

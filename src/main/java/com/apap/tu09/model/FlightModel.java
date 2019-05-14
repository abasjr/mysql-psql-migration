package com.apap.tu09.model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


/**
 * FlightModel
 */
@Entity
@Table(name = "flight")
public class FlightModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "flight_number", nullable = false)
    private String flightNumber;

    @NotNull
    @Size(max = 100)
    @Column(name = "origin", nullable = false)
    private String origin;

    @NotNull
    @Size(max = 100)
    @Column(name = "destination", nullable = false)
    private String destination;

    @NotNull
    @Column(name = "time", nullable = false)
    private Date time;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pilot_licenseNumber", referencedColumnName = "license_number")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private PilotModel pilot;

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }
    /**
     * @param flightNumber the flightNumber to set
     */
    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
    /**
     * @param origin the origin to set
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @param destination the destination to set
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }
    /**
     * @param pilot the pilot to set
     */
    public void setPilot(PilotModel pilot) {
        this.pilot = pilot;
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    /**
     * @return the flightNumber
     */
    public String getFlightNumber() {
        return flightNumber;
    }
    /**
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @return the destination
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @return the time
     */
    public Date getTime() {
        return time;
    }
    /**
     * @return the pilot
     */
    public PilotModel getPilot() {
        return pilot;
    }
}
package com.battap.vpn.domain;

import com.battap.vpn.domain.enumeration.Platform;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Client.
 */
@Document(collection = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @Field("client_ip")
    private String clientIP;

    @Field("qr_code")
    private byte[] qrCode;

    @Field("qr_code_content_type")
    private String qrCodeContentType;

    @Field("email")
    private String email;

    @NotNull
    @Field("status")
    private Boolean status;

    @Field("platform")
    private Platform platform;

    @Field("description")
    private String description;

    @Field("bytes_received")
    private String bytesReceived;

    @Field("bytes_sent")
    private String bytesSent;

    @NotNull
    @Field("start_date")
    private Instant startDate;

    @Field("last_update_date")
    private Instant lastUpdateDate;

    @DBRef
    @Field("tunnel")
    private Tunnel tunnel;

    @DBRef
    @Field("wg")
    @JsonIgnoreProperties(value = { "clients", "virServer" }, allowSetters = true)
    private Wg wg;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Client id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Client name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientIP() {
        return this.clientIP;
    }

    public Client clientIP(String clientIP) {
        this.setClientIP(clientIP);
        return this;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public byte[] getQrCode() {
        return this.qrCode;
    }

    public Client qrCode(byte[] qrCode) {
        this.setQrCode(qrCode);
        return this;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeContentType() {
        return this.qrCodeContentType;
    }

    public Client qrCodeContentType(String qrCodeContentType) {
        this.qrCodeContentType = qrCodeContentType;
        return this;
    }

    public void setQrCodeContentType(String qrCodeContentType) {
        this.qrCodeContentType = qrCodeContentType;
    }

    public String getEmail() {
        return this.email;
    }

    public Client email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public Client status(Boolean status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Platform getPlatform() {
        return this.platform;
    }

    public Client platform(Platform platform) {
        this.setPlatform(platform);
        return this;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return this.description;
    }

    public Client description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBytesReceived() {
        return this.bytesReceived;
    }

    public Client bytesReceived(String bytesReceived) {
        this.setBytesReceived(bytesReceived);
        return this;
    }

    public void setBytesReceived(String bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getBytesSent() {
        return this.bytesSent;
    }

    public Client bytesSent(String bytesSent) {
        this.setBytesSent(bytesSent);
        return this;
    }

    public void setBytesSent(String bytesSent) {
        this.bytesSent = bytesSent;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public Client startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public Client lastUpdateDate(Instant lastUpdateDate) {
        this.setLastUpdateDate(lastUpdateDate);
        return this;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Tunnel getTunnel() {
        return this.tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }

    public Client tunnel(Tunnel tunnel) {
        this.setTunnel(tunnel);
        return this;
    }

    public Wg getWg() {
        return this.wg;
    }

    public void setWg(Wg wg) {
        this.wg = wg;
    }

    public Client wg(Wg wg) {
        this.setWg(wg);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", clientIP='" + getClientIP() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            ", qrCodeContentType='" + getQrCodeContentType() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", description='" + getDescription() + "'" +
            ", bytesReceived='" + getBytesReceived() + "'" +
            ", bytesSent='" + getBytesSent() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            "}";
    }
}

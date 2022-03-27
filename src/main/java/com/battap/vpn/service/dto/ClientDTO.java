package com.battap.vpn.service.dto;

import com.battap.vpn.domain.enumeration.Platform;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.battap.vpn.domain.Client} entity.
 */
public class ClientDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    private String clientIP;

    private byte[] qrCode;

    private String qrCodeContentType;
    private String email;

    @NotNull
    private Boolean status;

    private Platform platform;

    private String description;

    private String bytesReceived;

    private String bytesSent;

    @NotNull
    private Instant startDate;

    private Instant lastUpdateDate;

    private TunnelDTO tunnel;

    private WgDTO wg;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public byte[] getQrCode() {
        return qrCode;
    }

    public void setQrCode(byte[] qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeContentType() {
        return qrCodeContentType;
    }

    public void setQrCodeContentType(String qrCodeContentType) {
        this.qrCodeContentType = qrCodeContentType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(String bytesReceived) {
        this.bytesReceived = bytesReceived;
    }

    public String getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(String bytesSent) {
        this.bytesSent = bytesSent;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Instant lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public TunnelDTO getTunnel() {
        return tunnel;
    }

    public void setTunnel(TunnelDTO tunnel) {
        this.tunnel = tunnel;
    }

    public WgDTO getWg() {
        return wg;
    }

    public void setWg(WgDTO wg) {
        this.wg = wg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientDTO)) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clientDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", clientIP='" + getClientIP() + "'" +
            ", qrCode='" + getQrCode() + "'" +
            ", email='" + getEmail() + "'" +
            ", status='" + getStatus() + "'" +
            ", platform='" + getPlatform() + "'" +
            ", description='" + getDescription() + "'" +
            ", bytesReceived='" + getBytesReceived() + "'" +
            ", bytesSent='" + getBytesSent() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", lastUpdateDate='" + getLastUpdateDate() + "'" +
            ", tunnel=" + getTunnel() +
            ", wg=" + getWg() +
            "}";
    }
}

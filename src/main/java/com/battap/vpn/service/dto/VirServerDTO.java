package com.battap.vpn.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.battap.vpn.domain.VirServer} entity.
 */
public class VirServerDTO implements Serializable {

    private String id;

    @NotNull
    private String vpsName;

    @NotNull
    private String remoteHost;

    @NotNull
    private String remoteUserName;

    @NotNull
    private String remotePassword;

    @NotNull
    private Integer remotePort;

    private Integer sessionTimeOut;

    private Integer chanelTimeOut;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVpsName() {
        return vpsName;
    }

    public void setVpsName(String vpsName) {
        this.vpsName = vpsName;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getRemoteUserName() {
        return remoteUserName;
    }

    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public Integer getSessionTimeOut() {
        return sessionTimeOut;
    }

    public void setSessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public Integer getChanelTimeOut() {
        return chanelTimeOut;
    }

    public void setChanelTimeOut(Integer chanelTimeOut) {
        this.chanelTimeOut = chanelTimeOut;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VirServerDTO)) {
            return false;
        }

        VirServerDTO virServerDTO = (VirServerDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, virServerDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VirServerDTO{" +
            "id='" + getId() + "'" +
            ", vpsName='" + getVpsName() + "'" +
            ", remoteHost='" + getRemoteHost() + "'" +
            ", remoteUserName='" + getRemoteUserName() + "'" +
            ", remotePassword='" + getRemotePassword() + "'" +
            ", remotePort=" + getRemotePort() +
            ", sessionTimeOut=" + getSessionTimeOut() +
            ", chanelTimeOut=" + getChanelTimeOut() +
            "}";
    }
}

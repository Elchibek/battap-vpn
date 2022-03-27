package com.battap.vpn.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A VirServer.
 */
@Document(collection = "vir_server")
public class VirServer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("vps_name")
    private String vpsName;

    @NotNull
    @Field("remote_host")
    private String remoteHost;

    @NotNull
    @Field("remote_user_name")
    private String remoteUserName;

    @NotNull
    @Field("remote_password")
    private String remotePassword;

    @NotNull
    @Field("remote_port")
    private Integer remotePort;

    @Field("session_time_out")
    private Integer sessionTimeOut;

    @Field("chanel_time_out")
    private Integer chanelTimeOut;

    @DBRef
    @Field("wgconf")
    @JsonIgnoreProperties(value = { "clients", "virServer" }, allowSetters = true)
    private Set<Wg> wgconfs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public VirServer id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVpsName() {
        return this.vpsName;
    }

    public VirServer vpsName(String vpsName) {
        this.setVpsName(vpsName);
        return this;
    }

    public void setVpsName(String vpsName) {
        this.vpsName = vpsName;
    }

    public String getRemoteHost() {
        return this.remoteHost;
    }

    public VirServer remoteHost(String remoteHost) {
        this.setRemoteHost(remoteHost);
        return this;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public String getRemoteUserName() {
        return this.remoteUserName;
    }

    public VirServer remoteUserName(String remoteUserName) {
        this.setRemoteUserName(remoteUserName);
        return this;
    }

    public void setRemoteUserName(String remoteUserName) {
        this.remoteUserName = remoteUserName;
    }

    public String getRemotePassword() {
        return this.remotePassword;
    }

    public VirServer remotePassword(String remotePassword) {
        this.setRemotePassword(remotePassword);
        return this;
    }

    public void setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
    }

    public Integer getRemotePort() {
        return this.remotePort;
    }

    public VirServer remotePort(Integer remotePort) {
        this.setRemotePort(remotePort);
        return this;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public Integer getSessionTimeOut() {
        return this.sessionTimeOut;
    }

    public VirServer sessionTimeOut(Integer sessionTimeOut) {
        this.setSessionTimeOut(sessionTimeOut);
        return this;
    }

    public void setSessionTimeOut(Integer sessionTimeOut) {
        this.sessionTimeOut = sessionTimeOut;
    }

    public Integer getChanelTimeOut() {
        return this.chanelTimeOut;
    }

    public VirServer chanelTimeOut(Integer chanelTimeOut) {
        this.setChanelTimeOut(chanelTimeOut);
        return this;
    }

    public void setChanelTimeOut(Integer chanelTimeOut) {
        this.chanelTimeOut = chanelTimeOut;
    }

    public Set<Wg> getWgconfs() {
        return this.wgconfs;
    }

    public void setWgconfs(Set<Wg> wgs) {
        if (this.wgconfs != null) {
            this.wgconfs.forEach(i -> i.setVirServer(null));
        }
        if (wgs != null) {
            wgs.forEach(i -> i.setVirServer(this));
        }
        this.wgconfs = wgs;
    }

    public VirServer wgconfs(Set<Wg> wgs) {
        this.setWgconfs(wgs);
        return this;
    }

    public VirServer addWgconf(Wg wg) {
        this.wgconfs.add(wg);
        wg.setVirServer(this);
        return this;
    }

    public VirServer removeWgconf(Wg wg) {
        this.wgconfs.remove(wg);
        wg.setVirServer(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VirServer)) {
            return false;
        }
        return id != null && id.equals(((VirServer) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VirServer{" +
            "id=" + getId() +
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

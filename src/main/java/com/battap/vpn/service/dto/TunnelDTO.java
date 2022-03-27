package com.battap.vpn.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.battap.vpn.domain.Tunnel} entity.
 */
public class TunnelDTO implements Serializable {

    private String id;

    @NotNull
    private String clientPrivateKey;

    @NotNull
    private String clientPubKey;

    @NotNull
    private String address;

    @NotNull
    private String dns;

    @NotNull
    private String serverPublicKey;

    private String presharedKey;

    @NotNull
    private String andpoint;

    @NotNull
    private String allowedIPs;

    private Integer persistentKeepalive;

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    public String getClientPubKey() {
        return clientPubKey;
    }

    public void setClientPubKey(String clientPubKey) {
        this.clientPubKey = clientPubKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public String getPresharedKey() {
        return presharedKey;
    }

    public void setPresharedKey(String presharedKey) {
        this.presharedKey = presharedKey;
    }

    public String getAndpoint() {
        return andpoint;
    }

    public void setAndpoint(String andpoint) {
        this.andpoint = andpoint;
    }

    public String getAllowedIPs() {
        return allowedIPs;
    }

    public void setAllowedIPs(String allowedIPs) {
        this.allowedIPs = allowedIPs;
    }

    public Integer getPersistentKeepalive() {
        return persistentKeepalive;
    }

    public void setPersistentKeepalive(Integer persistentKeepalive) {
        this.persistentKeepalive = persistentKeepalive;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TunnelDTO)) {
            return false;
        }

        TunnelDTO tunnelDTO = (TunnelDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tunnelDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TunnelDTO{" +
            "id='" + getId() + "'" +
            ", clientPrivateKey='" + getClientPrivateKey() + "'" +
            ", clientPubKey='" + getClientPubKey() + "'" +
            ", address='" + getAddress() + "'" +
            ", dns='" + getDns() + "'" +
            ", serverPublicKey='" + getServerPublicKey() + "'" +
            ", presharedKey='" + getPresharedKey() + "'" +
            ", andpoint='" + getAndpoint() + "'" +
            ", allowedIPs='" + getAllowedIPs() + "'" +
            ", persistentKeepalive=" + getPersistentKeepalive() +
            ", text='" + getText() + "'" +
            "}";
    }
}

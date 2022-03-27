package com.battap.vpn.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Tunnel.
 */
@Document(collection = "tunnel")
public class Tunnel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("client_private_key")
    private String clientPrivateKey;

    @NotNull
    @Field("client_pub_key")
    private String clientPubKey;

    @NotNull
    @Field("address")
    private String address;

    @NotNull
    @Field("dns")
    private String dns;

    @NotNull
    @Field("server_public_key")
    private String serverPublicKey;

    @Field("preshared_key")
    private String presharedKey;

    @NotNull
    @Field("andpoint")
    private String andpoint;

    @NotNull
    @Field("allowed_i_ps")
    private String allowedIPs;

    @Field("persistent_keepalive")
    private Integer persistentKeepalive;

    @Field("text")
    private String text;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Tunnel id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClientPrivateKey() {
        return this.clientPrivateKey;
    }

    public Tunnel clientPrivateKey(String clientPrivateKey) {
        this.setClientPrivateKey(clientPrivateKey);
        return this;
    }

    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    public String getClientPubKey() {
        return this.clientPubKey;
    }

    public Tunnel clientPubKey(String clientPubKey) {
        this.setClientPubKey(clientPubKey);
        return this;
    }

    public void setClientPubKey(String clientPubKey) {
        this.clientPubKey = clientPubKey;
    }

    public String getAddress() {
        return this.address;
    }

    public Tunnel address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDns() {
        return this.dns;
    }

    public Tunnel dns(String dns) {
        this.setDns(dns);
        return this;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getServerPublicKey() {
        return this.serverPublicKey;
    }

    public Tunnel serverPublicKey(String serverPublicKey) {
        this.setServerPublicKey(serverPublicKey);
        return this;
    }

    public void setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public String getPresharedKey() {
        return this.presharedKey;
    }

    public Tunnel presharedKey(String presharedKey) {
        this.setPresharedKey(presharedKey);
        return this;
    }

    public void setPresharedKey(String presharedKey) {
        this.presharedKey = presharedKey;
    }

    public String getAndpoint() {
        return this.andpoint;
    }

    public Tunnel andpoint(String andpoint) {
        this.setAndpoint(andpoint);
        return this;
    }

    public void setAndpoint(String andpoint) {
        this.andpoint = andpoint;
    }

    public String getAllowedIPs() {
        return this.allowedIPs;
    }

    public Tunnel allowedIPs(String allowedIPs) {
        this.setAllowedIPs(allowedIPs);
        return this;
    }

    public void setAllowedIPs(String allowedIPs) {
        this.allowedIPs = allowedIPs;
    }

    public Integer getPersistentKeepalive() {
        return this.persistentKeepalive;
    }

    public Tunnel persistentKeepalive(Integer persistentKeepalive) {
        this.setPersistentKeepalive(persistentKeepalive);
        return this;
    }

    public void setPersistentKeepalive(Integer persistentKeepalive) {
        this.persistentKeepalive = persistentKeepalive;
    }

    public String getText() {
        return this.text;
    }

    public Tunnel text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tunnel)) {
            return false;
        }
        return id != null && id.equals(((Tunnel) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tunnel{" +
            "id=" + getId() +
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

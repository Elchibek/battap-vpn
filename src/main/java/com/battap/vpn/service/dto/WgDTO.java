package com.battap.vpn.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.battap.vpn.domain.Wg} entity.
 */
public class WgDTO implements Serializable {

    private String id;

    private String privateKey;

    private String publicKey;

    @NotNull
    private String address;

    private Integer mtu;

    @NotNull
    private Integer listenPort;

    private String postUp;

    private String postDown;

    private String text;

    private VirServerDTO virServer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMtu() {
        return mtu;
    }

    public void setMtu(Integer mtu) {
        this.mtu = mtu;
    }

    public Integer getListenPort() {
        return listenPort;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public String getPostUp() {
        return postUp;
    }

    public void setPostUp(String postUp) {
        this.postUp = postUp;
    }

    public String getPostDown() {
        return postDown;
    }

    public void setPostDown(String postDown) {
        this.postDown = postDown;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public VirServerDTO getVirServer() {
        return virServer;
    }

    public void setVirServer(VirServerDTO virServer) {
        this.virServer = virServer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WgDTO)) {
            return false;
        }

        WgDTO wgDTO = (WgDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wgDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WgDTO{" +
            "id='" + getId() + "'" +
            ", privateKey='" + getPrivateKey() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", address='" + getAddress() + "'" +
            ", mtu=" + getMtu() +
            ", listenPort=" + getListenPort() +
            ", postUp='" + getPostUp() + "'" +
            ", postDown='" + getPostDown() + "'" +
            ", text='" + getText() + "'" +
            ", virServer=" + getVirServer() +
            "}";
    }
}

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
 * A Wg.
 */
@Document(collection = "wg")
public class Wg implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("private_key")
    private String privateKey;

    @NotNull
    @Field("public_key")
    private String publicKey;

    @NotNull
    @Field("address")
    private String address;

    @Field("mtu")
    private Integer mtu;

    @NotNull
    @Field("listen_port")
    private Integer listenPort;

    @Field("post_up")
    private String postUp;

    @Field("post_down")
    private String postDown;

    @Field("text")
    private String text;

    @DBRef
    @Field("client")
    @JsonIgnoreProperties(value = { "tunnel", "wg" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    @DBRef
    @Field("virServer")
    @JsonIgnoreProperties(value = { "wgconfs" }, allowSetters = true)
    private VirServer virServer;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Wg id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivateKey() {
        return this.privateKey;
    }

    public Wg privateKey(String privateKey) {
        this.setPrivateKey(privateKey);
        return this;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return this.publicKey;
    }

    public Wg publicKey(String publicKey) {
        this.setPublicKey(publicKey);
        return this;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getAddress() {
        return this.address;
    }

    public Wg address(String address) {
        this.setAddress(address);
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getMtu() {
        return this.mtu;
    }

    public Wg mtu(Integer mtu) {
        this.setMtu(mtu);
        return this;
    }

    public void setMtu(Integer mtu) {
        this.mtu = mtu;
    }

    public Integer getListenPort() {
        return this.listenPort;
    }

    public Wg listenPort(Integer listenPort) {
        this.setListenPort(listenPort);
        return this;
    }

    public void setListenPort(Integer listenPort) {
        this.listenPort = listenPort;
    }

    public String getPostUp() {
        return this.postUp;
    }

    public Wg postUp(String postUp) {
        this.setPostUp(postUp);
        return this;
    }

    public void setPostUp(String postUp) {
        this.postUp = postUp;
    }

    public String getPostDown() {
        return this.postDown;
    }

    public Wg postDown(String postDown) {
        this.setPostDown(postDown);
        return this;
    }

    public void setPostDown(String postDown) {
        this.postDown = postDown;
    }

    public String getText() {
        return this.text;
    }

    public Wg text(String text) {
        this.setText(text);
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        if (this.clients != null) {
            this.clients.forEach(i -> i.setWg(null));
        }
        if (clients != null) {
            clients.forEach(i -> i.setWg(this));
        }
        this.clients = clients;
    }

    public Wg clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Wg addClient(Client client) {
        this.clients.add(client);
        client.setWg(this);
        return this;
    }

    public Wg removeClient(Client client) {
        this.clients.remove(client);
        client.setWg(null);
        return this;
    }

    public VirServer getVirServer() {
        return this.virServer;
    }

    public void setVirServer(VirServer virServer) {
        this.virServer = virServer;
    }

    public Wg virServer(VirServer virServer) {
        this.setVirServer(virServer);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Wg)) {
            return false;
        }
        return id != null && id.equals(((Wg) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Wg{" +
            "id=" + getId() +
            ", privateKey='" + getPrivateKey() + "'" +
            ", publicKey='" + getPublicKey() + "'" +
            ", address='" + getAddress() + "'" +
            ", mtu=" + getMtu() +
            ", listenPort=" + getListenPort() +
            ", postUp='" + getPostUp() + "'" +
            ", postDown='" + getPostDown() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}

export interface ITunnel {
  id?: string;
  clientPrivateKey?: string;
  clientPubKey?: string;
  address?: string;
  dns?: string;
  serverPublicKey?: string;
  presharedKey?: string | null;
  andpoint?: string;
  allowedIPs?: string;
  persistentKeepalive?: number | null;
  text?: string | null;
}

export const defaultValue: Readonly<ITunnel> = {};

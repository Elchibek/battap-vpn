export interface IVirServer {
  id?: string;
  vpsName?: string;
  remoteHost?: string;
  remoteUserName?: string;
  remotePassword?: string;
  remotePort?: number;
  sessionTimeOut?: number | null;
  chanelTimeOut?: number | null;
}

export const defaultValue: Readonly<IVirServer> = {};

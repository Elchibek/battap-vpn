import { IClient } from 'app/shared/model/client.model';
import { IVirServer } from 'app/shared/model/vir-server.model';

export interface IWg {
  id?: string;
  privateKey?: string;
  publicKey?: string;
  address?: string;
  mtu?: number | null;
  listenPort?: number;
  postUp?: string | null;
  postDown?: string | null;
  text?: string | null;
  clients?: IClient[] | null;
  virServer?: IVirServer | null;
}

export const defaultValue: Readonly<IWg> = {};

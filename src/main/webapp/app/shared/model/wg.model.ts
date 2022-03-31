import { IVirServer } from 'app/shared/model/vir-server.model';
import { IClient } from 'app/shared/model/client.model';

export interface IWg {
  id?: string;
  name?: string;
  privateKey?: string;
  publicKey?: string;
  address?: string;
  mtu?: number | null;
  listenPort?: number;
  postUp?: string | null;
  postDown?: string | null;
  virServer?: IVirServer | null;
  clients?: IClient[] | null;
}

export const defaultValue: Readonly<IWg> = {};

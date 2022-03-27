import dayjs from 'dayjs';
import { ITunnel } from 'app/shared/model/tunnel.model';
import { IWg } from 'app/shared/model/wg.model';
import { Platform } from 'app/shared/model/enumerations/platform.model';

export interface IClient {
  id?: string;
  name?: string;
  clientIP?: string | null;
  qrCodeContentType?: string | null;
  qrCode?: string | null;
  email?: string | null;
  status?: boolean;
  platform?: Platform | null;
  description?: string | null;
  bytesReceived?: string | null;
  bytesSent?: string | null;
  startDate?: string;
  lastUpdateDate?: string | null;
  tunnel?: ITunnel | null;
  wg?: IWg | null;
}

export const defaultValue: Readonly<IClient> = {
  status: false,
};

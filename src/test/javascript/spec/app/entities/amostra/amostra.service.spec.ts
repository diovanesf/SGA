/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import AmostraService from '@/entities/amostra/amostra.service';
import { Amostra } from '@/shared/model/amostra.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('Amostra Service', () => {
    let service: AmostraService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new AmostraService();
      currentDate = new Date();
      elemDefault = new Amostra(
        0,
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataInicial: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find(123).then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a Amostra', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataInicial: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Amostra', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Amostra', async () => {
        const returnedFromService = Object.assign(
          {
            protocolo: 'BBBBBB',
            formaEnvio: 'BBBBBB',
            numeroAmostras: 1,
            especie: 'BBBBBB',
            numeroAnimais: 1,
            acometidos: 'BBBBBB',
            pricipalSuspeita: 'BBBBBB',
            dataInicial: dayjs(currentDate).format(DATE_FORMAT),
            materialRecebido: 'BBBBBB',
            acondicionamento: 'BBBBBB',
            condicaoMaterial: 'BBBBBB',
            status: 'BBBBBB',
            tipoMedVet: 'BBBBBB',
            valorTotal: 1,
            tipoPagamento: 'BBBBBB',
            tipo: 'BBBBBB',
            situacao: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataInicial: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Amostra', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Amostra', async () => {
        const patchObject = Object.assign(
          {
            protocolo: 'BBBBBB',
            formaEnvio: 'BBBBBB',
            numeroAmostras: 1,
            especie: 'BBBBBB',
            situacao: 'BBBBBB',
          },
          new Amostra()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataInicial: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Amostra', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Amostra', async () => {
        const returnedFromService = Object.assign(
          {
            protocolo: 'BBBBBB',
            formaEnvio: 'BBBBBB',
            numeroAmostras: 1,
            especie: 'BBBBBB',
            numeroAnimais: 1,
            acometidos: 'BBBBBB',
            pricipalSuspeita: 'BBBBBB',
            dataInicial: dayjs(currentDate).format(DATE_FORMAT),
            materialRecebido: 'BBBBBB',
            acondicionamento: 'BBBBBB',
            condicaoMaterial: 'BBBBBB',
            status: 'BBBBBB',
            tipoMedVet: 'BBBBBB',
            valorTotal: 1,
            tipoPagamento: 'BBBBBB',
            tipo: 'BBBBBB',
            situacao: 'BBBBBB',
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataInicial: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Amostra', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Amostra', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Amostra', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete(123)
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});

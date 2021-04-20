/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';
import dayjs from 'dayjs';

import { DATE_FORMAT } from '@/shared/date/filters';
import ExameService from '@/entities/exame/exame.service';
import { Exame } from '@/shared/model/exame.model';

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
  describe('Exame Service', () => {
    let service: ExameService;
    let elemDefault;
    let currentDate: Date;

    beforeEach(() => {
      service = new ExameService();
      currentDate = new Date();
      elemDefault = new Exame(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', currentDate, currentDate, 'AAAAAAA', 'AAAAAAA', 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign(
          {
            dataTeste: dayjs(currentDate).format(DATE_FORMAT),
            dataLeitura: dayjs(currentDate).format(DATE_FORMAT),
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

      it('should create a Exame', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            dataTeste: dayjs(currentDate).format(DATE_FORMAT),
            dataLeitura: dayjs(currentDate).format(DATE_FORMAT),
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataTeste: currentDate,
            dataLeitura: currentDate,
          },
          returnedFromService
        );

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a Exame', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a Exame', async () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            resultado: 'BBBBBB',
            dataTeste: dayjs(currentDate).format(DATE_FORMAT),
            dataLeitura: dayjs(currentDate).format(DATE_FORMAT),
            preenchimentoEspelho: 'BBBBBB',
            observacoes: 'BBBBBB',
            valor: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dataTeste: currentDate,
            dataLeitura: currentDate,
          },
          returnedFromService
        );
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a Exame', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a Exame', async () => {
        const patchObject = Object.assign(
          {
            nome: 'BBBBBB',
            resultado: 'BBBBBB',
            dataLeitura: dayjs(currentDate).format(DATE_FORMAT),
            preenchimentoEspelho: 'BBBBBB',
          },
          new Exame()
        );
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            dataTeste: currentDate,
            dataLeitura: currentDate,
          },
          returnedFromService
        );
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a Exame', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of Exame', async () => {
        const returnedFromService = Object.assign(
          {
            nome: 'BBBBBB',
            tipo: 'BBBBBB',
            resultado: 'BBBBBB',
            dataTeste: dayjs(currentDate).format(DATE_FORMAT),
            dataLeitura: dayjs(currentDate).format(DATE_FORMAT),
            preenchimentoEspelho: 'BBBBBB',
            observacoes: 'BBBBBB',
            valor: 1,
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dataTeste: currentDate,
            dataLeitura: currentDate,
          },
          returnedFromService
        );
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve().then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of Exame', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a Exame', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete(123).then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a Exame', async () => {
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

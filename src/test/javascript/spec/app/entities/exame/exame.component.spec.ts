/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ExameComponent from '@/entities/exame/exame.vue';
import ExameClass from '@/entities/exame/exame.component';
import ExameService from '@/entities/exame/exame.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Exame Management Component', () => {
    let wrapper: Wrapper<ExameClass>;
    let comp: ExameClass;
    let exameServiceStub: SinonStubbedInstance<ExameService>;

    beforeEach(() => {
      exameServiceStub = sinon.createStubInstance<ExameService>(ExameService);
      exameServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ExameClass>(ExameComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          exameService: () => exameServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      exameServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllExames();
      await comp.$nextTick();

      // THEN
      expect(exameServiceStub.retrieve.called).toBeTruthy();
      expect(comp.exames[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      exameServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeExame();
      await comp.$nextTick();

      // THEN
      expect(exameServiceStub.delete.called).toBeTruthy();
      expect(exameServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import VacinaComponent from '@/entities/vacina/vacina.vue';
import VacinaClass from '@/entities/vacina/vacina.component';
import VacinaService from '@/entities/vacina/vacina.service';

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
  describe('Vacina Management Component', () => {
    let wrapper: Wrapper<VacinaClass>;
    let comp: VacinaClass;
    let vacinaServiceStub: SinonStubbedInstance<VacinaService>;

    beforeEach(() => {
      vacinaServiceStub = sinon.createStubInstance<VacinaService>(VacinaService);
      vacinaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VacinaClass>(VacinaComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          vacinaService: () => vacinaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      vacinaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVacinas();
      await comp.$nextTick();

      // THEN
      expect(vacinaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.vacinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      vacinaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeVacina();
      await comp.$nextTick();

      // THEN
      expect(vacinaServiceStub.delete.called).toBeTruthy();
      expect(vacinaServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

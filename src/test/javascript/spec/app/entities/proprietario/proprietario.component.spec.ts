/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import ProprietarioComponent from '@/entities/proprietario/proprietario.vue';
import ProprietarioClass from '@/entities/proprietario/proprietario.component';
import ProprietarioService from '@/entities/proprietario/proprietario.service';

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
  describe('Proprietario Management Component', () => {
    let wrapper: Wrapper<ProprietarioClass>;
    let comp: ProprietarioClass;
    let proprietarioServiceStub: SinonStubbedInstance<ProprietarioService>;

    beforeEach(() => {
      proprietarioServiceStub = sinon.createStubInstance<ProprietarioService>(ProprietarioService);
      proprietarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ProprietarioClass>(ProprietarioComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          proprietarioService: () => proprietarioServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      proprietarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllProprietarios();
      await comp.$nextTick();

      // THEN
      expect(proprietarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.proprietarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      proprietarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeProprietario();
      await comp.$nextTick();

      // THEN
      expect(proprietarioServiceStub.delete.called).toBeTruthy();
      expect(proprietarioServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

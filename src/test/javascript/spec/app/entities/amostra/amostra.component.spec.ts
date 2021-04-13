/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AmostraComponent from '@/entities/amostra/amostra.vue';
import AmostraClass from '@/entities/amostra/amostra.component';
import AmostraService from '@/entities/amostra/amostra.service';

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
  describe('Amostra Management Component', () => {
    let wrapper: Wrapper<AmostraClass>;
    let comp: AmostraClass;
    let amostraServiceStub: SinonStubbedInstance<AmostraService>;

    beforeEach(() => {
      amostraServiceStub = sinon.createStubInstance<AmostraService>(AmostraService);
      amostraServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AmostraClass>(AmostraComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          amostraService: () => amostraServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      amostraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAmostras();
      await comp.$nextTick();

      // THEN
      expect(amostraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.amostras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      amostraServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAmostra();
      await comp.$nextTick();

      // THEN
      expect(amostraServiceStub.delete.called).toBeTruthy();
      expect(amostraServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

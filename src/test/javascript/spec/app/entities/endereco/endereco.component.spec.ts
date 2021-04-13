/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import EnderecoComponent from '@/entities/endereco/endereco.vue';
import EnderecoClass from '@/entities/endereco/endereco.component';
import EnderecoService from '@/entities/endereco/endereco.service';

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
  describe('Endereco Management Component', () => {
    let wrapper: Wrapper<EnderecoClass>;
    let comp: EnderecoClass;
    let enderecoServiceStub: SinonStubbedInstance<EnderecoService>;

    beforeEach(() => {
      enderecoServiceStub = sinon.createStubInstance<EnderecoService>(EnderecoService);
      enderecoServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EnderecoClass>(EnderecoComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          enderecoService: () => enderecoServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      enderecoServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEnderecos();
      await comp.$nextTick();

      // THEN
      expect(enderecoServiceStub.retrieve.called).toBeTruthy();
      expect(comp.enderecos[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      enderecoServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeEndereco();
      await comp.$nextTick();

      // THEN
      expect(enderecoServiceStub.delete.called).toBeTruthy();
      expect(enderecoServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

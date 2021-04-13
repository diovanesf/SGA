/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MidiaComponent from '@/entities/midia/midia.vue';
import MidiaClass from '@/entities/midia/midia.component';
import MidiaService from '@/entities/midia/midia.service';

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
  describe('Midia Management Component', () => {
    let wrapper: Wrapper<MidiaClass>;
    let comp: MidiaClass;
    let midiaServiceStub: SinonStubbedInstance<MidiaService>;

    beforeEach(() => {
      midiaServiceStub = sinon.createStubInstance<MidiaService>(MidiaService);
      midiaServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MidiaClass>(MidiaComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          midiaService: () => midiaServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      midiaServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMidias();
      await comp.$nextTick();

      // THEN
      expect(midiaServiceStub.retrieve.called).toBeTruthy();
      expect(comp.midias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      midiaServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMidia();
      await comp.$nextTick();

      // THEN
      expect(midiaServiceStub.delete.called).toBeTruthy();
      expect(midiaServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

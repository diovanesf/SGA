/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import SubamostraComponent from '@/entities/subamostra/subamostra.vue';
import SubamostraClass from '@/entities/subamostra/subamostra.component';
import SubamostraService from '@/entities/subamostra/subamostra.service';

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
  describe('Subamostra Management Component', () => {
    let wrapper: Wrapper<SubamostraClass>;
    let comp: SubamostraClass;
    let subamostraServiceStub: SinonStubbedInstance<SubamostraService>;

    beforeEach(() => {
      subamostraServiceStub = sinon.createStubInstance<SubamostraService>(SubamostraService);
      subamostraServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SubamostraClass>(SubamostraComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          subamostraService: () => subamostraServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      subamostraServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSubamostras();
      await comp.$nextTick();

      // THEN
      expect(subamostraServiceStub.retrieve.called).toBeTruthy();
      expect(comp.subamostras[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      subamostraServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeSubamostra();
      await comp.$nextTick();

      // THEN
      expect(subamostraServiceStub.delete.called).toBeTruthy();
      expect(subamostraServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

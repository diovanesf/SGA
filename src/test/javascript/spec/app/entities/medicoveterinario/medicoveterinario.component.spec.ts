/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import MedicoveterinarioComponent from '@/entities/medicoveterinario/medicoveterinario.vue';
import MedicoveterinarioClass from '@/entities/medicoveterinario/medicoveterinario.component';
import MedicoveterinarioService from '@/entities/medicoveterinario/medicoveterinario.service';

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
  describe('Medicoveterinario Management Component', () => {
    let wrapper: Wrapper<MedicoveterinarioClass>;
    let comp: MedicoveterinarioClass;
    let medicoveterinarioServiceStub: SinonStubbedInstance<MedicoveterinarioService>;

    beforeEach(() => {
      medicoveterinarioServiceStub = sinon.createStubInstance<MedicoveterinarioService>(MedicoveterinarioService);
      medicoveterinarioServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MedicoveterinarioClass>(MedicoveterinarioComponent, {
        store,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          medicoveterinarioService: () => medicoveterinarioServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      medicoveterinarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMedicoveterinarios();
      await comp.$nextTick();

      // THEN
      expect(medicoveterinarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.medicoveterinarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      medicoveterinarioServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeMedicoveterinario();
      await comp.$nextTick();

      // THEN
      expect(medicoveterinarioServiceStub.delete.called).toBeTruthy();
      expect(medicoveterinarioServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

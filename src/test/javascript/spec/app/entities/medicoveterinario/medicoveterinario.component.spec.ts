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
localVue.component('jhi-sort-indicator', {});
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
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
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

    it('should load a page', async () => {
      // GIVEN
      medicoveterinarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(medicoveterinarioServiceStub.retrieve.called).toBeTruthy();
      expect(comp.medicoveterinarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      medicoveterinarioServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(medicoveterinarioServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      medicoveterinarioServiceStub.retrieve.reset();
      medicoveterinarioServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(medicoveterinarioServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.medicoveterinarios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
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

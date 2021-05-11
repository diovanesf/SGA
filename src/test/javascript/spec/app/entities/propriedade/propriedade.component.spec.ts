/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import PropriedadeComponent from '@/entities/propriedade/propriedade.vue';
import PropriedadeClass from '@/entities/propriedade/propriedade.component';
import PropriedadeService from '@/entities/propriedade/propriedade.service';

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
  describe('Propriedade Management Component', () => {
    let wrapper: Wrapper<PropriedadeClass>;
    let comp: PropriedadeClass;
    let propriedadeServiceStub: SinonStubbedInstance<PropriedadeService>;

    beforeEach(() => {
      propriedadeServiceStub = sinon.createStubInstance<PropriedadeService>(PropriedadeService);
      propriedadeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PropriedadeClass>(PropriedadeComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          propriedadeService: () => propriedadeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      propriedadeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPropriedades();
      await comp.$nextTick();

      // THEN
      expect(propriedadeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.propriedades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      propriedadeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(propriedadeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.propriedades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      propriedadeServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(propriedadeServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      propriedadeServiceStub.retrieve.reset();
      propriedadeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(propriedadeServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.propriedades[0]).toEqual(jasmine.objectContaining({ id: 123 }));
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
      propriedadeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removePropriedade();
      await comp.$nextTick();

      // THEN
      expect(propriedadeServiceStub.delete.called).toBeTruthy();
      expect(propriedadeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});

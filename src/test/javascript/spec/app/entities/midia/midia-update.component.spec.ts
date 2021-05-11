/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import MidiaUpdateComponent from '@/entities/midia/midia-update.vue';
import MidiaClass from '@/entities/midia/midia-update.component';
import MidiaService from '@/entities/midia/midia.service';

import AmostraService from '@/entities/amostra/amostra.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Midia Management Update Component', () => {
    let wrapper: Wrapper<MidiaClass>;
    let comp: MidiaClass;
    let midiaServiceStub: SinonStubbedInstance<MidiaService>;

    beforeEach(() => {
      midiaServiceStub = sinon.createStubInstance<MidiaService>(MidiaService);

      wrapper = shallowMount<MidiaClass>(MidiaUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          midiaService: () => midiaServiceStub,

          amostraService: () => new AmostraService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.midia = entity;
        midiaServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(midiaServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.midia = entity;
        midiaServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(midiaServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMidia = { id: 123 };
        midiaServiceStub.find.resolves(foundMidia);
        midiaServiceStub.retrieve.resolves([foundMidia]);

        // WHEN
        comp.beforeRouteEnter({ params: { midiaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.midia).toBe(foundMidia);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import SubamostraUpdateComponent from '@/entities/subamostra/subamostra-update.vue';
import SubamostraClass from '@/entities/subamostra/subamostra-update.component';
import SubamostraService from '@/entities/subamostra/subamostra.service';

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
  describe('Subamostra Management Update Component', () => {
    let wrapper: Wrapper<SubamostraClass>;
    let comp: SubamostraClass;
    let subamostraServiceStub: SinonStubbedInstance<SubamostraService>;

    beforeEach(() => {
      subamostraServiceStub = sinon.createStubInstance<SubamostraService>(SubamostraService);

      wrapper = shallowMount<SubamostraClass>(SubamostraUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          subamostraService: () => subamostraServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.subamostra = entity;
        subamostraServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subamostraServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.subamostra = entity;
        subamostraServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(subamostraServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubamostra = { id: 123 };
        subamostraServiceStub.find.resolves(foundSubamostra);
        subamostraServiceStub.retrieve.resolves([foundSubamostra]);

        // WHEN
        comp.beforeRouteEnter({ params: { subamostraId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.subamostra).toBe(foundSubamostra);
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

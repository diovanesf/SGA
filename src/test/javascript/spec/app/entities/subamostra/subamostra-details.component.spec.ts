/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SubamostraDetailComponent from '@/entities/subamostra/subamostra-details.vue';
import SubamostraClass from '@/entities/subamostra/subamostra-details.component';
import SubamostraService from '@/entities/subamostra/subamostra.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Subamostra Management Detail Component', () => {
    let wrapper: Wrapper<SubamostraClass>;
    let comp: SubamostraClass;
    let subamostraServiceStub: SinonStubbedInstance<SubamostraService>;

    beforeEach(() => {
      subamostraServiceStub = sinon.createStubInstance<SubamostraService>(SubamostraService);

      wrapper = shallowMount<SubamostraClass>(SubamostraDetailComponent, {
        store,
        localVue,
        router,
        provide: { subamostraService: () => subamostraServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSubamostra = { id: 123 };
        subamostraServiceStub.find.resolves(foundSubamostra);

        // WHEN
        comp.retrieveSubamostra(123);
        await comp.$nextTick();

        // THEN
        expect(comp.subamostra).toBe(foundSubamostra);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSubamostra = { id: 123 };
        subamostraServiceStub.find.resolves(foundSubamostra);

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

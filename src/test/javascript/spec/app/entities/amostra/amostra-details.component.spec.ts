/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AmostraDetailComponent from '@/entities/amostra/amostra-details.vue';
import AmostraClass from '@/entities/amostra/amostra-details.component';
import AmostraService from '@/entities/amostra/amostra.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Amostra Management Detail Component', () => {
    let wrapper: Wrapper<AmostraClass>;
    let comp: AmostraClass;
    let amostraServiceStub: SinonStubbedInstance<AmostraService>;

    beforeEach(() => {
      amostraServiceStub = sinon.createStubInstance<AmostraService>(AmostraService);

      wrapper = shallowMount<AmostraClass>(AmostraDetailComponent, {
        store,
        localVue,
        router,
        provide: { amostraService: () => amostraServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAmostra = { id: 123 };
        amostraServiceStub.find.resolves(foundAmostra);

        // WHEN
        comp.retrieveAmostra(123);
        await comp.$nextTick();

        // THEN
        expect(comp.amostra).toBe(foundAmostra);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAmostra = { id: 123 };
        amostraServiceStub.find.resolves(foundAmostra);

        // WHEN
        comp.beforeRouteEnter({ params: { amostraId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.amostra).toBe(foundAmostra);
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

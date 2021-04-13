/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MidiaDetailComponent from '@/entities/midia/midia-details.vue';
import MidiaClass from '@/entities/midia/midia-details.component';
import MidiaService from '@/entities/midia/midia.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Midia Management Detail Component', () => {
    let wrapper: Wrapper<MidiaClass>;
    let comp: MidiaClass;
    let midiaServiceStub: SinonStubbedInstance<MidiaService>;

    beforeEach(() => {
      midiaServiceStub = sinon.createStubInstance<MidiaService>(MidiaService);

      wrapper = shallowMount<MidiaClass>(MidiaDetailComponent, {
        store,
        localVue,
        router,
        provide: { midiaService: () => midiaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMidia = { id: 123 };
        midiaServiceStub.find.resolves(foundMidia);

        // WHEN
        comp.retrieveMidia(123);
        await comp.$nextTick();

        // THEN
        expect(comp.midia).toBe(foundMidia);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMidia = { id: 123 };
        midiaServiceStub.find.resolves(foundMidia);

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

/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ExameDetailComponent from '@/entities/exame/exame-details.vue';
import ExameClass from '@/entities/exame/exame-details.component';
import ExameService from '@/entities/exame/exame.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Exame Management Detail Component', () => {
    let wrapper: Wrapper<ExameClass>;
    let comp: ExameClass;
    let exameServiceStub: SinonStubbedInstance<ExameService>;

    beforeEach(() => {
      exameServiceStub = sinon.createStubInstance<ExameService>(ExameService);

      wrapper = shallowMount<ExameClass>(ExameDetailComponent, {
        store,
        localVue,
        router,
        provide: { exameService: () => exameServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundExame = { id: 123 };
        exameServiceStub.find.resolves(foundExame);

        // WHEN
        comp.retrieveExame(123);
        await comp.$nextTick();

        // THEN
        expect(comp.exame).toBe(foundExame);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundExame = { id: 123 };
        exameServiceStub.find.resolves(foundExame);

        // WHEN
        comp.beforeRouteEnter({ params: { exameId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.exame).toBe(foundExame);
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

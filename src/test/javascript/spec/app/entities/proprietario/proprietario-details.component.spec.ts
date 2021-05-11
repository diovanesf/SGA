/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ProprietarioDetailComponent from '@/entities/proprietario/proprietario-details.vue';
import ProprietarioClass from '@/entities/proprietario/proprietario-details.component';
import ProprietarioService from '@/entities/proprietario/proprietario.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Proprietario Management Detail Component', () => {
    let wrapper: Wrapper<ProprietarioClass>;
    let comp: ProprietarioClass;
    let proprietarioServiceStub: SinonStubbedInstance<ProprietarioService>;

    beforeEach(() => {
      proprietarioServiceStub = sinon.createStubInstance<ProprietarioService>(ProprietarioService);

      wrapper = shallowMount<ProprietarioClass>(ProprietarioDetailComponent, {
        store,
        localVue,
        router,
        provide: { proprietarioService: () => proprietarioServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundProprietario = { id: 123 };
        proprietarioServiceStub.find.resolves(foundProprietario);

        // WHEN
        comp.retrieveProprietario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.proprietario).toBe(foundProprietario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundProprietario = { id: 123 };
        proprietarioServiceStub.find.resolves(foundProprietario);

        // WHEN
        comp.beforeRouteEnter({ params: { proprietarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.proprietario).toBe(foundProprietario);
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

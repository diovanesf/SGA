/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import VacinaDetailComponent from '@/entities/vacina/vacina-details.vue';
import VacinaClass from '@/entities/vacina/vacina-details.component';
import VacinaService from '@/entities/vacina/vacina.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Vacina Management Detail Component', () => {
    let wrapper: Wrapper<VacinaClass>;
    let comp: VacinaClass;
    let vacinaServiceStub: SinonStubbedInstance<VacinaService>;

    beforeEach(() => {
      vacinaServiceStub = sinon.createStubInstance<VacinaService>(VacinaService);

      wrapper = shallowMount<VacinaClass>(VacinaDetailComponent, {
        store,
        localVue,
        router,
        provide: { vacinaService: () => vacinaServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundVacina = { id: 123 };
        vacinaServiceStub.find.resolves(foundVacina);

        // WHEN
        comp.retrieveVacina(123);
        await comp.$nextTick();

        // THEN
        expect(comp.vacina).toBe(foundVacina);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundVacina = { id: 123 };
        vacinaServiceStub.find.resolves(foundVacina);

        // WHEN
        comp.beforeRouteEnter({ params: { vacinaId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.vacina).toBe(foundVacina);
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

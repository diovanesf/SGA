/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import MedicoveterinarioDetailComponent from '@/entities/medicoveterinario/medicoveterinario-details.vue';
import MedicoveterinarioClass from '@/entities/medicoveterinario/medicoveterinario-details.component';
import MedicoveterinarioService from '@/entities/medicoveterinario/medicoveterinario.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Medicoveterinario Management Detail Component', () => {
    let wrapper: Wrapper<MedicoveterinarioClass>;
    let comp: MedicoveterinarioClass;
    let medicoveterinarioServiceStub: SinonStubbedInstance<MedicoveterinarioService>;

    beforeEach(() => {
      medicoveterinarioServiceStub = sinon.createStubInstance<MedicoveterinarioService>(MedicoveterinarioService);

      wrapper = shallowMount<MedicoveterinarioClass>(MedicoveterinarioDetailComponent, {
        store,
        localVue,
        router,
        provide: { medicoveterinarioService: () => medicoveterinarioServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundMedicoveterinario = { id: 123 };
        medicoveterinarioServiceStub.find.resolves(foundMedicoveterinario);

        // WHEN
        comp.retrieveMedicoveterinario(123);
        await comp.$nextTick();

        // THEN
        expect(comp.medicoveterinario).toBe(foundMedicoveterinario);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundMedicoveterinario = { id: 123 };
        medicoveterinarioServiceStub.find.resolves(foundMedicoveterinario);

        // WHEN
        comp.beforeRouteEnter({ params: { medicoveterinarioId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.medicoveterinario).toBe(foundMedicoveterinario);
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

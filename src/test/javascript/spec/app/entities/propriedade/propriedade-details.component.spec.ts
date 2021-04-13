/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PropriedadeDetailComponent from '@/entities/propriedade/propriedade-details.vue';
import PropriedadeClass from '@/entities/propriedade/propriedade-details.component';
import PropriedadeService from '@/entities/propriedade/propriedade.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Propriedade Management Detail Component', () => {
    let wrapper: Wrapper<PropriedadeClass>;
    let comp: PropriedadeClass;
    let propriedadeServiceStub: SinonStubbedInstance<PropriedadeService>;

    beforeEach(() => {
      propriedadeServiceStub = sinon.createStubInstance<PropriedadeService>(PropriedadeService);

      wrapper = shallowMount<PropriedadeClass>(PropriedadeDetailComponent, {
        store,
        localVue,
        router,
        provide: { propriedadeService: () => propriedadeServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPropriedade = { id: 123 };
        propriedadeServiceStub.find.resolves(foundPropriedade);

        // WHEN
        comp.retrievePropriedade(123);
        await comp.$nextTick();

        // THEN
        expect(comp.propriedade).toBe(foundPropriedade);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPropriedade = { id: 123 };
        propriedadeServiceStub.find.resolves(foundPropriedade);

        // WHEN
        comp.beforeRouteEnter({ params: { propriedadeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.propriedade).toBe(foundPropriedade);
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

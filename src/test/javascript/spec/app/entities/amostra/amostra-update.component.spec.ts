/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import AmostraUpdateComponent from '@/entities/amostra/amostra-update.vue';
import AmostraClass from '@/entities/amostra/amostra-update.component';
import AmostraService from '@/entities/amostra/amostra.service';

import UserService from '@/admin/user-management/user-management.service';

import MidiaService from '@/entities/midia/midia.service';

import SubamostraService from '@/entities/subamostra/subamostra.service';

import ExameService from '@/entities/exame/exame.service';

import PropriedadeService from '@/entities/propriedade/propriedade.service';

import MedicoveterinarioService from '@/entities/medicoveterinario/medicoveterinario.service';

import VacinaService from '@/entities/vacina/vacina.service';

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
  describe('Amostra Management Update Component', () => {
    let wrapper: Wrapper<AmostraClass>;
    let comp: AmostraClass;
    let amostraServiceStub: SinonStubbedInstance<AmostraService>;

    beforeEach(() => {
      amostraServiceStub = sinon.createStubInstance<AmostraService>(AmostraService);

      wrapper = shallowMount<AmostraClass>(AmostraUpdateComponent, {
        store,
        localVue,
        router,
        provide: {
          amostraService: () => amostraServiceStub,

          userService: () => new UserService(),

          midiaService: () => new MidiaService(),

          subamostraService: () => new SubamostraService(),

          exameService: () => new ExameService(),

          propriedadeService: () => new PropriedadeService(),

          medicoveterinarioService: () => new MedicoveterinarioService(),

          vacinaService: () => new VacinaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.amostra = entity;
        amostraServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(amostraServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.amostra = entity;
        amostraServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(amostraServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAmostra = { id: 123 };
        amostraServiceStub.find.resolves(foundAmostra);
        amostraServiceStub.retrieve.resolves([foundAmostra]);

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

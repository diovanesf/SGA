<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h2 v-if="username" id="settings-title">
          <span
            >Configurações do usuário [<strong>{{ username }}</strong
            >]</span
          >
        </h2>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>Configurações salvas!</strong>
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>Este e-mail já está em uso!</strong> Por favor, insira outro.
        </div>

        <form name="form" id="settings-form" role="form" v-on:submit.prevent="save()" v-if="settingsAccount" novalidate>
          <div class="form-group">
            <label class="form-control-label" for="firstName">Nome</label>
            <input
              type="text"
              class="form-control"
              id="firstName"
              name="firstName"
              :class="{ valid: !$v.settingsAccount.firstName.$invalid, invalid: $v.settingsAccount.firstName.$invalid }"
              v-model="$v.settingsAccount.firstName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="firstname"
            />
            <div v-if="$v.settingsAccount.firstName.$anyDirty && $v.settingsAccount.firstName.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.required"> Seu nome é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.minLength">
                Seu nome deve conter ao menos 1 caracter.
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.firstName.maxLength">
                Seu nome não deve ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="lastName">Sobrenome</label>
            <input
              type="text"
              class="form-control"
              id="lastName"
              name="lastName"
              :class="{ valid: !$v.settingsAccount.lastName.$invalid, invalid: $v.settingsAccount.lastName.$invalid }"
              v-model="$v.settingsAccount.lastName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="lastname"
            />
            <div v-if="$v.settingsAccount.lastName.$anyDirty && $v.settingsAccount.lastName.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.required"> Seu sobrenome é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.minLength">
                Seu sobrenome deve conter ao menos 1 caracter.
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.lastName.maxLength">
                Seu sobrenome não deve ultrapassar 50 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">E-mail</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !$v.settingsAccount.email.$invalid, invalid: $v.settingsAccount.email.$invalid }"
              v-model="$v.settingsAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="email"
            />
            <div v-if="$v.settingsAccount.email.$anyDirty && $v.settingsAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.required"> Seu e-mail é necessário. </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.email"> Seu e-mail é invalido. </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.minLength">
                Seu e-mail deve conter ao menos 5 caracteres.
              </small>
              <small class="form-text text-danger" v-if="!$v.settingsAccount.email.maxLength">
                Seu e-mail não deve ultrapassar 100 caracteres.
              </small>
            </div>
          </div>
          <div class="form-group" v-if="languages && Object.keys(languages).length > 1">
            <label for="langKey">Idioma</label>
            <select class="form-control" id="langKey" name="langKey" v-model="settingsAccount.langKey" data-cy="langKey">
              <option v-for="(language, key) in languages" :value="key" :key="`lang-${key}`">{{ language.name }}</option>
            </select>
          </div>
          <button type="submit" :disabled="$v.settingsAccount.$invalid" class="btn btn-primary" data-cy="submit">Salvar</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./settings.component.ts"></script>

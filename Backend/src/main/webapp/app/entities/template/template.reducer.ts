import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITemplate, defaultValue } from 'app/shared/model/template.model';

export const ACTION_TYPES = {
  FETCH_TEMPLATE_LIST: 'template/FETCH_TEMPLATE_LIST',
  FETCH_TEMPLATE: 'template/FETCH_TEMPLATE',
  CREATE_TEMPLATE: 'template/CREATE_TEMPLATE',
  UPDATE_TEMPLATE: 'template/UPDATE_TEMPLATE',
  DELETE_TEMPLATE: 'template/DELETE_TEMPLATE',
  RESET: 'template/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITemplate>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type TemplateState = Readonly<typeof initialState>;

// Reducer

export default (state: TemplateState = initialState, action): TemplateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_TEMPLATE):
    case REQUEST(ACTION_TYPES.UPDATE_TEMPLATE):
    case REQUEST(ACTION_TYPES.DELETE_TEMPLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATE):
    case FAILURE(ACTION_TYPES.CREATE_TEMPLATE):
    case FAILURE(ACTION_TYPES.UPDATE_TEMPLATE):
    case FAILURE(ACTION_TYPES.DELETE_TEMPLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEMPLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_TEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEMPLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/templates';

// Actions

export const getEntities: ICrudGetAllAction<ITemplate> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATE_LIST,
    payload: axios.get<ITemplate>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ITemplate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATE,
    payload: axios.get<ITemplate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ITemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEMPLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITemplate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEMPLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITemplate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEMPLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

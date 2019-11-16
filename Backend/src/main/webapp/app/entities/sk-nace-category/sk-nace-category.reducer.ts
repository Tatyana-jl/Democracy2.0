import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISkNaceCategory, defaultValue } from 'app/shared/model/sk-nace-category.model';

export const ACTION_TYPES = {
  FETCH_SKNACECATEGORY_LIST: 'skNaceCategory/FETCH_SKNACECATEGORY_LIST',
  FETCH_SKNACECATEGORY: 'skNaceCategory/FETCH_SKNACECATEGORY',
  CREATE_SKNACECATEGORY: 'skNaceCategory/CREATE_SKNACECATEGORY',
  UPDATE_SKNACECATEGORY: 'skNaceCategory/UPDATE_SKNACECATEGORY',
  DELETE_SKNACECATEGORY: 'skNaceCategory/DELETE_SKNACECATEGORY',
  RESET: 'skNaceCategory/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISkNaceCategory>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SkNaceCategoryState = Readonly<typeof initialState>;

// Reducer

export default (state: SkNaceCategoryState = initialState, action): SkNaceCategoryState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SKNACECATEGORY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SKNACECATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SKNACECATEGORY):
    case REQUEST(ACTION_TYPES.UPDATE_SKNACECATEGORY):
    case REQUEST(ACTION_TYPES.DELETE_SKNACECATEGORY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SKNACECATEGORY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SKNACECATEGORY):
    case FAILURE(ACTION_TYPES.CREATE_SKNACECATEGORY):
    case FAILURE(ACTION_TYPES.UPDATE_SKNACECATEGORY):
    case FAILURE(ACTION_TYPES.DELETE_SKNACECATEGORY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKNACECATEGORY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SKNACECATEGORY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SKNACECATEGORY):
    case SUCCESS(ACTION_TYPES.UPDATE_SKNACECATEGORY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SKNACECATEGORY):
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

const apiUrl = 'api/sk-nace-categories';

// Actions

export const getEntities: ICrudGetAllAction<ISkNaceCategory> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SKNACECATEGORY_LIST,
    payload: axios.get<ISkNaceCategory>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISkNaceCategory> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SKNACECATEGORY,
    payload: axios.get<ISkNaceCategory>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISkNaceCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SKNACECATEGORY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISkNaceCategory> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SKNACECATEGORY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISkNaceCategory> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SKNACECATEGORY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMunicipality, defaultValue } from 'app/shared/model/municipality.model';

export const ACTION_TYPES = {
  FETCH_MUNICIPALITY_LIST: 'municipality/FETCH_MUNICIPALITY_LIST',
  FETCH_MUNICIPALITY: 'municipality/FETCH_MUNICIPALITY',
  CREATE_MUNICIPALITY: 'municipality/CREATE_MUNICIPALITY',
  UPDATE_MUNICIPALITY: 'municipality/UPDATE_MUNICIPALITY',
  DELETE_MUNICIPALITY: 'municipality/DELETE_MUNICIPALITY',
  RESET: 'municipality/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMunicipality>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MunicipalityState = Readonly<typeof initialState>;

// Reducer

export default (state: MunicipalityState = initialState, action): MunicipalityState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MUNICIPALITY_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MUNICIPALITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MUNICIPALITY):
    case REQUEST(ACTION_TYPES.UPDATE_MUNICIPALITY):
    case REQUEST(ACTION_TYPES.DELETE_MUNICIPALITY):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MUNICIPALITY_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MUNICIPALITY):
    case FAILURE(ACTION_TYPES.CREATE_MUNICIPALITY):
    case FAILURE(ACTION_TYPES.UPDATE_MUNICIPALITY):
    case FAILURE(ACTION_TYPES.DELETE_MUNICIPALITY):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUNICIPALITY_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_MUNICIPALITY):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MUNICIPALITY):
    case SUCCESS(ACTION_TYPES.UPDATE_MUNICIPALITY):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MUNICIPALITY):
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

const apiUrl = 'api/municipalities';

// Actions

export const getEntities: ICrudGetAllAction<IMunicipality> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MUNICIPALITY_LIST,
    payload: axios.get<IMunicipality>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMunicipality> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MUNICIPALITY,
    payload: axios.get<IMunicipality>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMunicipality> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MUNICIPALITY,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMunicipality> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MUNICIPALITY,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMunicipality> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MUNICIPALITY,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

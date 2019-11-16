import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFinanceAnalysis, defaultValue } from 'app/shared/model/finance-analysis.model';

export const ACTION_TYPES = {
  FETCH_FINANCEANALYSIS_LIST: 'financeAnalysis/FETCH_FINANCEANALYSIS_LIST',
  FETCH_FINANCEANALYSIS: 'financeAnalysis/FETCH_FINANCEANALYSIS',
  CREATE_FINANCEANALYSIS: 'financeAnalysis/CREATE_FINANCEANALYSIS',
  UPDATE_FINANCEANALYSIS: 'financeAnalysis/UPDATE_FINANCEANALYSIS',
  DELETE_FINANCEANALYSIS: 'financeAnalysis/DELETE_FINANCEANALYSIS',
  RESET: 'financeAnalysis/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFinanceAnalysis>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FinanceAnalysisState = Readonly<typeof initialState>;

// Reducer

export default (state: FinanceAnalysisState = initialState, action): FinanceAnalysisState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FINANCEANALYSIS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FINANCEANALYSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FINANCEANALYSIS):
    case REQUEST(ACTION_TYPES.UPDATE_FINANCEANALYSIS):
    case REQUEST(ACTION_TYPES.DELETE_FINANCEANALYSIS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FINANCEANALYSIS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FINANCEANALYSIS):
    case FAILURE(ACTION_TYPES.CREATE_FINANCEANALYSIS):
    case FAILURE(ACTION_TYPES.UPDATE_FINANCEANALYSIS):
    case FAILURE(ACTION_TYPES.DELETE_FINANCEANALYSIS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCEANALYSIS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_FINANCEANALYSIS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FINANCEANALYSIS):
    case SUCCESS(ACTION_TYPES.UPDATE_FINANCEANALYSIS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FINANCEANALYSIS):
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

const apiUrl = 'api/finance-analyses';

// Actions

export const getEntities: ICrudGetAllAction<IFinanceAnalysis> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCEANALYSIS_LIST,
    payload: axios.get<IFinanceAnalysis>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFinanceAnalysis> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FINANCEANALYSIS,
    payload: axios.get<IFinanceAnalysis>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFinanceAnalysis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FINANCEANALYSIS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFinanceAnalysis> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FINANCEANALYSIS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFinanceAnalysis> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FINANCEANALYSIS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});

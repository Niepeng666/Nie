package com.common.com.base;

import com.common.com.http.DataCall;
import com.common.com.http.NetworkManager;
import java.lang.reflect.ParameterizedType;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
/**
 * @author 聂俊鹏
 * @date 2018/12/28 11:30
 * qq:2241659414
 */
public abstract class WDPresenter<T> {
    public final static int REQUEST_TYPE_DEFAULT = 0;//默认IRquest
    public final static int REQUEST_TYPE_SDK_BD = 100;//例：这个为请求百度的接口，请求结构为另外一种
    private DataCall dataCall;
    private boolean running;//是否运行，防止重复请求，这里没有用rxjava的重复过滤（个人感觉重复过滤用在多次点击上比较好，请求从体验角度最好不要间隔过滤）
    private Disposable disposable;//rxjava层取消请求
    protected T iRequest;

    public WDPresenter(DataCall dataCall) {
        this.dataCall = dataCall;
        Class<T> tClass = getTClass();
        iRequest = NetworkManager.instance().create(getRequestType(),tClass);
    }
    protected abstract Observable getModel(Object... args);


    /**
     * 获取泛型对相应的Class对象
     * @return
     */
    private Class<T>  getTClass(){
        //返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type。
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        //返回表示此类型实际类型参数的 Type 对象的数组()，想要获取第二个泛型的Class，所以索引写1
        return (Class)type.getActualTypeArguments()[0];//<T>
    }
    /**
     * 请求类型，方便修改不同的IRequest接口和Rtrofit
     */
    protected int getRequestType() {
        return REQUEST_TYPE_DEFAULT;
    }







}

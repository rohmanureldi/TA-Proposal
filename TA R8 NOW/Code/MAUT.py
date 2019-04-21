import pandas as pd

data = pd.read_excel (r'Lembar Survey.xlsx','Lembar Survey',index_col=None, na_values=['NA'], usecols = "G:Q") 
data.columns
df = pd.DataFrame(data)

produk_index=df.iloc[3:30,0].reset_index(drop=True)
pelayanan_index = df.iloc[30:50,0].reset_index(drop=True)
pengelolaan_index = df.iloc[50:52,0].reset_index(drop=True)


produk_value=pd.DataFrame()
pelayanan_value=pd.DataFrame()
pengelolaan_value=pd.DataFrame()


for i in range(1,df.shape[1]):
    produk_value.insert(i-1,i-1,(df.iloc[3:30,i].reset_index(drop=True)).values.astype(int))
    pelayanan_value.insert(i-1,i-1,(df.iloc[30:50,i].reset_index(drop=True)).values.astype(int))
    pengelolaan_value.insert(i-1,i-1,(df.iloc[50:52,i].reset_index(drop=True)).values.astype(int))
#    produk_matrix.insert(i-1,i-1,produk_value[i])

#produk_value=df.iloc[3:30,1].reset_index(drop=True)
#pelayanan_value = df.iloc[30:50,1].reset_index(drop=True)
#pengelolaan_value = df.iloc[50:52,1].reset_index(drop=True)
#
#produk_value2=df.iloc[3:30,2].reset_index(drop=True)
#pelayanan_value2=df.iloc[30:50,2].reset_index(drop=True)
#pengelolaan_value2=df.iloc[50:52,2].reset_index(drop=True)
#
#produk_value=pd.concat([produk_value,produk_value2],ignore_index=True,axis=1)
##pelayanan_value=pd.concat([produk_value,pelayanan_value2],ignore_index=True,axis=1)
#pengelolaan_value=pd.concat([produk_value,pengelolaan_value2],ignore_index=True,axis=1)
    
print(produk_value[0][3])
print(produk_index[0])

def hitung_bobot(index,listValue,j):
    sum=0
    for i in range (0, len(index)):
        if(index[i]=="M" and listValue[j][i]==1):
            sum+=1
        if(index[i]=="TM" and listValue[j][i]==1):
            sum+=0.5
    return sum


print(hitung_bobot(produk_index,produk_value,0))

produk_bobot=pd.DataFrame()
pelayanan_bobot=pd.DataFrame()
pengelolaan_bobot=pd.DataFrame()
for k in range(0,produk_value.shape[1]):
    produk_bobot.insert(k,('hotel '+str(k+1)),[(hitung_bobot(produk_index,produk_value,k))/20.5])
    pelayanan_bobot.insert(k,('hotel '+str(k+1)),[(hitung_bobot(pelayanan_index,pelayanan_value,k))/14])
    pengelolaan_bobot.insert(k,('hotel '+str(k+1)),[(hitung_bobot(pengelolaan_index,pengelolaan_value,k))/2])


produk_bobot=((hitung_bobot(produk_index,produk_value,0))/20.5)
pelayanan_bobot=((hitung_bobot(pelayanan_index,pelayanan_value,0))/14)
pengelolaan_bobot=((hitung_bobot(pengelolaan_index,pengelolaan_value,0))/2)

produk_bobot2=(hitung_bobot(produk_index,produk_value,1)/20.5)
pelayanan_bobot2=(hitung_bobot(pelayanan_index,pelayanan_value,1)/14)
pengelolaan_bobot2=(hitung_bobot(pengelolaan_index,pengelolaan_value,1)/2)



preferensi={
        'produk':0.561644,
        'pelayanan':0.383562,
        'pengelolaan':0.054795}



pembobotan = pd.DataFrame({
        'alternatif':['hotel1','hotel2','preferensi'],
        'produk':[produk_bobot,produk_bobot2,0.561644],
        'pelayanan':[pelayanan_bobot,pelayanan_bobot2,0.383562],
        'pengelolaan':[pengelolaan_bobot/36.5,pengelolaan_bobot2,0.054795]})

pembobotan=pembobotan.set_index('alternatif')

print(len(pembobotan))

def normalisasi():
    hasil_normalisasi_array={}
    karakteristik=['produk','pelayanan','pengelolaan']
    for i in range(0,(len(pembobotan))-1):
        pre_hasil=0
        for j in karakteristik:
            bobot_alternatif=pembobotan[j][i]
            x_min=pembobotan[j].min()
            x_max=pembobotan[j].max()
            pre_hasil+=preferensi[j]*(bobot_alternatif-x_min)/(x_max+x_min)
            
            
        hasil_normalisasi_array.update({pembobotan.index[i]:pre_hasil})
        
    return hasil_normalisasi_array

normalisasi_hotel=normalisasi()
normalisasi_hotel=sorted(normalisasi_hotel.items(), key=lambda kv: kv[1])
normalisasi_hotel.reverse()         #normalisasi_hotel is tuple


    
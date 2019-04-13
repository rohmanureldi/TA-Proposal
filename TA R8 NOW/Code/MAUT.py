import pandas as pd

data = pd.read_excel (r'Lembar Survey.xlsx','Lembar Survey',index_col=None, na_values=['NA'], usecols = "G,H,I") 
data.columns
df = pd.DataFrame(data)

produk_index=df.iloc[3:30,0].reset_index(drop=True)
pelayanan_index = df.iloc[30:50,0].reset_index(drop=True)
pengelolaan_index = df.iloc[50:52,0].reset_index(drop=True)


produk_value=df.iloc[3:30,1].reset_index(drop=True)
pelayanan_value = df.iloc[30:50,1].reset_index(drop=True)
pengelolaan_value = df.iloc[50:52,1].reset_index(drop=True)

produk_value2=df.iloc[3:30,2].reset_index(drop=True)
pelayanan_value2=df.iloc[30:50,2].reset_index(drop=True)
pengelolaan_value2=df.iloc[50:52,2].reset_index(drop=True)

produk_value=pd.concat([produk_value,produk_value2],ignore_index=True,axis=1)
pelayanan_value=pd.concat([produk_value,pelayanan_value2],ignore_index=True,axis=1)
pengelolaan_value=pd.concat([produk_value,pengelolaan_value2],ignore_index=True,axis=1)

def hitung_bobot(index,value,j):
    sum=0
    for i in range (0, len(index)):
        if(index[i]=="M" and value[j][i]==1):
            sum+=1
        if(index[i]=="TM" and value[j][i]==1):
            sum+=0.5
    return sum


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

print(pembobotan.index[0])

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


    
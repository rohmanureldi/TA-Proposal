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




produk_bobot=hitung_bobot(produk_index,produk_value,0)
pelayanan_bobot=hitung_bobot(pelayanan_index,pelayanan_value,0)
pengelolaan_bobot=hitung_bobot(pengelolaan_index,pengelolaan_value,0)

produk_bobot2=hitung_bobot(produk_index,produk_value,1)
pelayanan_bobot2=hitung_bobot(pelayanan_index,pelayanan_value,1)
pengelolaan_bobot2=hitung_bobot(pengelolaan_index,pengelolaan_value,1)

pembobotan = pd.DataFrame({
        'alternatif':['hotel1','hotel2','preferensi'],
        'produk':[produk_bobot/36.5,produk_bobot2/36.5,0.561644],
        'pelayanan':[pelayanan_bobot/36.5,pelayanan_bobot2/36.5,0.383562],
        'pengelolaan':[pengelolaan_bobot/36.5,pengelolaan_bobot2/36.5,0.054795]})

pembobotan=pembobotan.set_index('alternatif')
print(pembobotan['produk'][2])

def normalisasi(alternatif):
    bobot_alternatif=alternatif
    
    


    
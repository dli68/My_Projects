function [ table ] = make_table( songName )

[y,fs] = audioread(songName);
y=mean(y,2);
y=mean(y,2);
y=y-mean(y);
y=resample(y,8000,fs);
[S,F,T]=spectrogram(y,512,256,512,8000);
S=log(abs(S)+1);

P=ones(size(S));
threshhold = prctile(reshape(S,1,numel(S)),30*max(T)/(257*344)*100);
threshholdmap = ones(size(S))*threshhold;
for m=1:9
    for n=1:9
        if(m~=5 || n~=5)
            CS=circshift(S,[m-5,n-5]);
            P = P.*((S-CS)>0 .* (S-threshholdmap)>0);
        end
    end
end

fanout=3;
tem_fanout=0;
table=NaN(numel(T)*numel(F),4);
pt=1;
flag=0;
for m=1:numel(T)
    for n=1:numel(F)
        if(P(n,m)==1)
            for k=5:15
                for q=-10:10
                    if(m+k<=numel(T) && n+q<=numel(F) && n+q>0)
                        if(P(n+q,m+k)==1)
                            table(pt,:)=[F(n) F(n+q) T(m) T(m+k)-T(m)];
                            pt=pt+1;
                            tem_fanout=tem_fanout+1;
                        end
                        if(tem_fanout==fanout)
                            flag=1;
                            break;
                        end
                    end
                end
                if(flag==1)
                    flag=0;
                    tem_fanout=0;
                    break;
                end
            end
        end
    end
end
table = table(~isnan(table(:,1)),:);

end

